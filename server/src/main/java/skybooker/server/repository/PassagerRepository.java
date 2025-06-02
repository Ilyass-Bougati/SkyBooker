package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Passager;

import java.util.List;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, Long> {
}
