package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.*;
import skybooker.server.enums.EtatReservation;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.*;
import skybooker.server.service.*;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ClientRepository clientRepository;
    private final VolRepository volRepository;
    private final PassagerRepository passagerRepository;
    private final ClasseRepository classeRepository;
    Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final BilletRepository billetRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, BilletRepository billetRepository, ClientRepository clientRepository, VolRepository volRepository, PassagerRepository passagerRepository, ClasseRepository classeRepository) {
        this.reservationRepository = reservationRepository;
        this.billetRepository = billetRepository;
        this.clientRepository = clientRepository;
        this.volRepository = volRepository;
        this.passagerRepository = passagerRepository;
        this.classeRepository = classeRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation
                .map(ReservationDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationDTO createDTO(ReservationDTO reservationDTO) {
        Client client = clientRepository.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        Vol vol = volRepository.findById(reservationDTO.getVolId())
                .orElseThrow(() -> new NotFoundException("Vol not found"));

        Reservation reservation = new Reservation(reservationDTO);
        reservation.setClient(client);
        reservation.setVol(vol);
        Reservation savedReservation = reservationRepository.save(reservation);

        // creating billets
        Passager passager;
        Classe classe;
        for (ReservationDTO.PassagerData passagerData : reservationDTO.getPassagers()) {
            passager = passagerRepository.findById(passagerData.getPassagerId())
                    .orElseThrow(() -> new NotFoundException("Passager not found"));
            classe = classeRepository.findById(passagerData.getClassId())
                    .orElseThrow(() -> new NotFoundException("Classe not found"));

            Billet billet = new Billet();
            billet.setReservation(savedReservation);
            billet.setClasse(classe);
            billet.setPassager(passager);

            Set<Capacite> capacites = reservation.getVol().getAvion().getCapacites();
            Capacite capacite = capacites
                    .stream().filter(c ->
                            c.getClasse().getId() == billet.getClasse().getId()
                    ).toList().get(0);

            Integer maxSiege = billetRepository.getMaxSiege(passagerData.getClassId(), reservationDTO.getVolId());

            // FIXME : This could fail miserably
            // TODO : I forgot why this could fail...
            billet.setSiege(maxSiege == null || maxSiege < capacite.getBorneInf() ? capacite.getBorneInf() : maxSiege + 1);

            Billet savedBillet = billetRepository.save(billet);
            savedReservation.getBillets().add(savedBillet);
        }

        return new ReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO updateDTO(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(reservationDTO.getId())
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        Vol vol = volRepository.findById(reservationDTO.getVolId())
                .orElseThrow(() -> new NotFoundException("Vol not found"));

        // updating the reservation
        reservation.setVol(vol);
        reservation.setPrixTotal(reservationDTO.getPrixTotal());
        reservation.setEtat(reservationDTO.getEtat());

        // saving the updated reservation
        return new ReservationDTO(reservationRepository.save(reservation));
    }

    @Override
    public void checkInClient(Long reservationId) {
        reservationRepository.modifyEtat(reservationId, EtatReservation.CHECKED_IN);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Resevation not found"));
        Set<Billet> billets = reservation.getBillets();
        // TODO : Not sure if we want to delete the billets
        for (Billet billet : billets) {
            billetRepository.deleteById(billet.getId());
        }
    }
}
