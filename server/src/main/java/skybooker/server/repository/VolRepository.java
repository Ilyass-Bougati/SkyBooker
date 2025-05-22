package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Vol;

import java.util.List;

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


    @Query("SELECT v.avion.id, COUNT(b.id), SUM(cap.capacite) " +
            "FROM Vol v JOIN v.avion a ON v.avion.id = a.id JOIN Capacite cap ON a.id = cap.avion.id JOIN Reservation r ON v.id = r.vol.id JOIN r.billets b " +
            "WHERE v.avion.id = :avionId AND EXTRACT(YEAR FROM v.dateDepart) = :year " +
            "GROUP BY v.avion.id")
    List<Object[]> countPassengersAndSumCapacityByAvionAndYear(@Param("avionId") Long avionId, @Param("year") Integer year);


    @Query("SELECT v.avion.id, COUNT(b.id), SUM(cap.capacite) " +
            "FROM Vol v JOIN v.avion a ON v.avion.id = a.id JOIN Capacite cap ON a.id = cap.avion.id JOIN Reservation r ON v.id = r.vol.id JOIN r.billets b " +
            "WHERE v.avion.id = :avionId AND EXTRACT(YEAR FROM v.dateDepart) = :year AND EXTRACT(QUARTER FROM v.dateDepart) = :quarter " +
            "GROUP BY v.avion.id")
    List<Object[]> countPassengersAndSumCapacityByAvionAndYearAndQuarter(@Param("avionId") Long avionId, @Param("year") Integer year, @Param("quarter") Integer quarter);


    @Query("SELECT v.id, COUNT(b.id), SUM(cap.capacite) " +
            "FROM Vol v JOIN v.avion a ON v.avion.id = a.id JOIN Capacite cap ON a.id = cap.avion.id JOIN Reservation r ON v.id = r.vol.id JOIN r.billets b " +
            "WHERE EXTRACT(YEAR FROM v.dateDepart) = :year " +
            "GROUP BY v.id")
    List<Object[]> countPassengersAndSumCapacityByYear(@Param("year") Integer year);

    @Query("SELECT v.id, COUNT(b.id), SUM(cap.capacite) " +
            "FROM Vol v JOIN v.avion a ON v.avion.id = a.id JOIN Capacite cap ON a.id = cap.avion.id JOIN Reservation r ON v.id = r.vol.id JOIN r.billets b " +
            "WHERE EXTRACT(YEAR FROM v.dateDepart) = :year AND EXTRACT(QUARTER FROM v.dateDepart) = :quarter " +
            "GROUP BY v.id")
    List<Object[]> countPassengersAndSumCapacityByYearAndQuarter(@Param("year") Integer year, @Param("quarter") Integer quarter);

}