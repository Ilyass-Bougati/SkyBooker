package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.entity.Billet;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {

    @Query("SELECT COUNT(b) = 0 FROM Billet b JOIN b.siege WHERE b.id = :siege AND b.reservation.vol.id = :volId")
    Boolean siegeLibre(Long volId, int siege);

    @Query("SELECT MAX(b.siege) FROM Billet b WHERE " +
            "b.classe.id = :classeId AND b.reservation.vol.id = :volId")
    Integer getMaxSiege(Long classeId, Long volId);

    @Query("SELECT b from Billet b WHERE b.passager.id=:passagerId")
    List<Billet> passagerBillets(Long passagerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Billet b WHERE b.etat = 'USED' AND b.reservation.reservedAt  < :oneYearAgo")
    void purgeUsedBillets(LocalDateTime oneYearAgo);
}
