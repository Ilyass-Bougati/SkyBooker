package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Capacite;

@Repository
public interface CapaciteRepository extends JpaRepository<Capacite, Long> {
}
