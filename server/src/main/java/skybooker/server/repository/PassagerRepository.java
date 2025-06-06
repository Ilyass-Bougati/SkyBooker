package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;

import java.util.List;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, Long> {
    @Query("SELECT p FROM Passager p WHERE p.client.id=:clientId")
    List<Passager> findAllByClientId(Long clientId);
}
