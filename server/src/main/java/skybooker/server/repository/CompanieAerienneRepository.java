package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.CompanieAerienne;

@Repository
public interface CompanieAerienneRepository extends JpaRepository<CompanieAerienne, Long> {
}
