package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Billet;
import skybooker.server.entity.Capacite;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;
import skybooker.server.repository.BilletRepository;
import skybooker.server.repository.ReservationRepository;
import skybooker.server.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

    Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final VolService volService;
    private final PassagerService passagerService;
    private final ClasseService classeService;
    private final BilletRepository billetRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ClientService clientService, VolService volService, PassagerService passagerService, ClasseService classeService, BilletRepository billetRepository) {
        this.reservationRepository = reservationRepository;
        this.clientService = clientService;
        this.volService = volService;
        this.passagerService = passagerService;
        this.classeService = classeService;
        this.billetRepository = billetRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.orElse(null);
    }

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    @Transactional
    public Reservation createDTO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO);
        reservation.setClient(clientService.findById(reservationDTO.getClientId()));
        reservation.setVol(volService.findById(reservationDTO.getVolId()));
        Reservation savedReservation = reservationRepository.save(reservation);

        // creating billets
        Passager passager;
        for (ReservationDTO.PassagerData passagerData : reservationDTO.getPassagers()) {
            passager = passagerService.findById(passagerData.getPassagerId());

            Billet billet = new Billet();
            billet.setReservation(savedReservation);
            billet.setClasse(classeService.findById(passagerData.getClassId()));
            billet.setPassager(passager);

            Set<Capacite> capacites = reservation.getVol().getAvion().getCapacites();
            Capacite capacite = capacites
                    .stream().filter(c ->
                            c.getClasse().getId() == billet.getClasse().getId()
                    ).toList().get(0);

            Integer maxSiege = billetRepository.getMaxSiege(passagerData.getClassId(), reservationDTO.getVolId());

            // FIXME : This could fail miserably
            billet.setSiege(maxSiege == null || maxSiege < capacite.getBorneInf() ? capacite.getBorneInf() : maxSiege + 1);

            logger.info("saving billet : " + billet.getPassager().getId());
            Billet savedBillet = billetRepository.save(billet);
            savedReservation.getBillets().add(savedBillet);
        }

        return savedReservation;
    }

    @Override
    @Transactional
    public Reservation updateDTO(ReservationDTO reservationDTO) {
        Reservation reservation = findById(reservationDTO.getId());
        if (reservationDTO.getClientId() != null) {
            // updating the reservation
            reservation.setClient(clientService.findById(reservationDTO.getClientId()));
            reservation.setVol(volService.findById(reservationDTO.getVolId()));
            reservation.setPrixTotal(reservationDTO.getPrixTotal());
            reservation.setEtat(reservationDTO.getEtat());
            reservation.setReservedAt(reservationDTO.getReservedAt());

            // saving the updated reservation
            return reservationRepository.save(reservation);
        } else {
            return null;
        }
    }
}
