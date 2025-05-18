package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> getByEmail(String email);
    void deleteByEmail(String email);
    Optional<Client> findByEmail(String email);

    @Query("select count(p) > 0 from Passager p where p.client.id = :clientId and p.id = :passagerId")
    Boolean passagerAddedByClient(Long clientId, Long passagerId);
}
