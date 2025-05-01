package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Avion;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {
}
