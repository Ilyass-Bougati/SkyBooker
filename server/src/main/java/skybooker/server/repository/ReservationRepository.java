package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.entity.Billet;
import skybooker.server.entity.Reservation;
import skybooker.server.enums.EtatReservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Transactional
    @Modifying
    @Query("update Reservation r set r.etat=:etat where r.id=:reservationId")
    void modifyEtat(Long reservationId, EtatReservation etat);

    @Transactional
    @Modifying
    @Query("delete Reservation r where r.reservedAt < :oneYearAgo")
    void purge(LocalDateTime oneYearAgo);
}
