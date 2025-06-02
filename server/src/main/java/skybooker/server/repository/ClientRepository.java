package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> getByEmail(String email);
    void deleteByEmail(String email);
    Optional<Client> findByEmail(String email);

    @Query("select case when count(p) > 0 then true else false end " +
            "from Passager p where p.client.id = :clientId and p.id = :passagerId")
    Boolean passagerAddedByClient(Long clientId, Long passagerId);

    @Query("SELECT c.id, COUNT(r.id), SUM(r.prixTotal), c.email " +
            "FROM Client c LEFT JOIN Reservation r ON c.id = r.client.id " +
            "JOIN c.role ro " +
            "WHERE ro.authority != 'ROLE_ADMIN' " +
            "GROUP BY c.id, c.email " +
            "ORDER BY c.email")
    List<Object[]> countReservationsAndTotalSpentPerClient();

    @Query("SELECT c.id, COUNT(p.id), c.email " +
            "FROM Client c LEFT JOIN Passager p ON c.id = p.client.id " +
            "JOIN c.role ro " +
            "WHERE ro.authority != 'ROLE_ADMIN' " +
            "GROUP BY c.id, c.email " +
            "ORDER BY c.email")
    List<Object[]> countPassengersPerClient();
}
