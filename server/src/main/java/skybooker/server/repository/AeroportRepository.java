package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Aeroport;

@Repository
public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
}
