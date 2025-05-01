package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Billet;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {
}
