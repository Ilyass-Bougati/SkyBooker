package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Billet;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {

    @Query("SELECT COUNT(b) = 0 FROM Billet b JOIN b.siege WHERE b.id = :siege AND b.reservation.vol.id = :volId")
    Boolean siegeLibre(Long volId, int siege);

    @Query("SELECT MAX(b.siege) FROM Billet b WHERE " +
            "b.classe.id = :classeId AND b.reservation.vol.id = :volId")

    Integer getMaxSiege(Long classeId, Long volId);
}
