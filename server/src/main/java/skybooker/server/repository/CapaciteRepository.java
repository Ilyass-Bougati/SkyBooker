package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Capacite;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapaciteRepository extends JpaRepository<Capacite, Long> {
    List<Capacite> findByAvionId(Long avionId);
}
