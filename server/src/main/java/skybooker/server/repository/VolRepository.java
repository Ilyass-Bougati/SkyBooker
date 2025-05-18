package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Vol;

import java.util.List;
import java.util.Objects;

@Repository
public interface VolRepository extends JpaRepository<Vol, Long> {

    @Query("SELECT v.id, SUM(c.capacite) " +
            "FROM Vol v JOIN v.avion a JOIN Capacite c ON a.id = c.avion.id " +
            "GROUP BY v.id")
    List<Object[]> findVolCapacities();


    @Query("SELECT r.vol.id, COUNT(b.id), SUM(r.prixTotal) " +
            "FROM Reservation r JOIN r.billets b " +
            "GROUP BY r.vol.id")
    List<Object[]> findVolBookedCountAndTotalRevenue();

    @Query("SELECT v.aeroportDepart.iataCode, v.aeroportArrive.iataCode, COUNT(b.id) " +
            "FROM Vol v JOIN Reservation r ON v.id = r.vol.id JOIN r.billets b " +
            "GROUP BY v.aeroportDepart.iataCode, v.aeroportArrive.iataCode " +
            "ORDER BY COUNT(b.id) DESC")
    List<Object[]> findPassengerCountPerRoute();

    @Query("SELECT v.aeroportDepart.iataCode, v.aeroportArrive.iataCode, SUM(r.prixTotal) " +
            "FROM Vol v JOIN Reservation r ON v.id = r.vol.id " +
            "GROUP BY v.aeroportDepart.iataCode, v.aeroportArrive.iataCode " +
            "ORDER BY SUM(r.prixTotal) DESC")
    List<Object[]> findTotalRevenuePerRoute();


}
