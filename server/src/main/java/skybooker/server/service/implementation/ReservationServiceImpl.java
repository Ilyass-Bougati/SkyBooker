package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VolService volService;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long aLong) {
        Optional<Reservation> reservation = reservationRepository.findById(aLong);
        return reservation.orElse(null);
    }

    @Override
    public Reservation create(Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    public Reservation update(Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        reservationRepository.deleteById(aLong);
    }

    @Override
    public void delete(Reservation entity) {
        reservationRepository.delete(entity);
    }

    @Override
    public Reservation createDTO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO);
        reservation.setClient(clientService.findById(reservationDTO.getClientId()));
        reservation.setVol(volService.findById(reservationDTO.getVolId()));
        return reservationRepository.save(reservation);
    }

    @Override
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
