package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Classe;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
}
