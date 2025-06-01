package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.entity.Categorie;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.service.CategorieService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categorieIdCache", key = "#id")
    public Categorie findById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categorieNameCache", key = "#name")
    public Categorie findByNom(String name) {
        Optional<Categorie> categorie = categorieRepository.findByNom(name);
        return categorie.orElse(null);
    }

    @Override
    @CachePut(value = "categorieIdCache", key = "#result.id")
    public Categorie create(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    @CachePut(value = "categorieIdCache", key = "#categorie.id")
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
    @CacheEvict(value = "categorieIdCache", key = "#id")
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "categorieIdCache", key = "#categorie.id")
    public void delete(Categorie categorie) {
        categorieRepository.delete(categorie);
    }
}
