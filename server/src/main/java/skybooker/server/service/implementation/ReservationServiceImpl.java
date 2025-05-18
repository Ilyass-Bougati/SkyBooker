package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Reservation;
import skybooker.server.repository.ReservationRepository;
import skybooker.server.service.ClientService;
import skybooker.server.service.ReservationService;
import skybooker.server.service.VolService;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final VolService volService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ClientService clientService, VolService volService) {
        this.reservationRepository = reservationRepository;
        this.clientService = clientService;
        this.volService = volService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long aLong) {
        Optional<Reservation> reservation = reservationRepository.findById(aLong);
        return reservation.orElse(null);
    }

    @Override
    @Transactional
    public Reservation create(Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    @Transactional
    public Reservation update(Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {
        reservationRepository.deleteById(aLong);
    }

    @Override
    @Transactional
    public void delete(Reservation entity) {
        reservationRepository.delete(entity);
    }

    @Override
    @Transactional
    public Reservation createDTO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO);
        reservation.setClient(clientService.findById(reservationDTO.getClientId()));
        reservation.setVol(volService.findById(reservationDTO.getVolId()));
        return reservationRepository.save(reservation);
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
