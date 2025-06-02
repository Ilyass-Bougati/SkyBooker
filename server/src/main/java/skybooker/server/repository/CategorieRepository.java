package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Categorie;
import skybooker.server.enums.CategorieNameEnum;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByNom(CategorieNameEnum name);
}
