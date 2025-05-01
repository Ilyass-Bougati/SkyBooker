package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Ville;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {
}
