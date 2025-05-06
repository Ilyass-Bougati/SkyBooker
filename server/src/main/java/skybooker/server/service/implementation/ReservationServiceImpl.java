package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Reservation;
import skybooker.server.repository.ReservationRepository;
import skybooker.server.service.ReservationService;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

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
}
