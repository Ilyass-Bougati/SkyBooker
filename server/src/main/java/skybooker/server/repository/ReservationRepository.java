package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Billet;
import skybooker.server.entity.Reservation;
import skybooker.server.enums.EtatReservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Query("update Reservation r set r.etat=:etat where r.id=:reservationId")
    void modifyEtat(Long reservationId, EtatReservation etat);
}
