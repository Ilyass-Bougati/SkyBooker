package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Categorie;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.service.CategorieService;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    public Categorie findById(Long aLong) {
        Optional<Categorie> categorie = categorieRepository.findById(aLong);
        return categorie.orElse(null);
    }

    @Override
    public Categorie create(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie update(Categorie categorie) {
        Categorie oldCategorie = this.findById(categorie.getId());
        if (oldCategorie != null) {
            oldCategorie.updateFields(categorie);
            return categorieRepository.save(oldCategorie);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    public void delete(Categorie entity) {
        categorieRepository.delete(entity);
    }
}
