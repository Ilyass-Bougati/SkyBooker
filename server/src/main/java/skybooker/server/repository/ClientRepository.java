package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> getByEmail(String email);
    void deleteByEmail(String email);
    Optional<Client> findByEmail(String email);
}
