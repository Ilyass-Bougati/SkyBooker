package skybooker.server.service.implementation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.CategorieDTO;
import skybooker.server.entity.Categorie;
import skybooker.server.enums.CategoryNameEnum;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.service.CategorieService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public List<CategorieDTO> findAll() {
        return categorieRepository.findAll()
                .stream().map(CategorieDTO::new).toList();
    }

    @Override
    public CategorieDTO createDTO(CategorieDTO categorieDTO) {
        Categorie categorie = new Categorie(categorieDTO);
        return new CategorieDTO(categorieRepository.save(categorie));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categorieIdCache", key = "#id")
    public CategorieDTO findById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie
                .map(CategorieDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categorieNameCache", key = "#name")
    public CategorieDTO findByNom(CategoryNameEnum name) {
        Optional<Categorie> categorie = categorieRepository.findByNom(name);
        return categorie
                .map(CategorieDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<CategorieDTO> getAllCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        return categories.stream().map(CategorieDTO::new).toList();
    }

    @Override
    @CachePut(value = "categorieIdCache", key = "#categorieDTO.id")
    public CategorieDTO updateDTO(CategorieDTO categorieDTO) {
        Optional<Categorie> oldCategorieOptional = categorieRepository.findById(categorieDTO.getId());
        if (oldCategorieOptional.isPresent()) {
            Categorie oldCategorie = oldCategorieOptional.get();

            // updating the categorie
            oldCategorie.setReduction(categorieDTO.getReduction());
            oldCategorie.setNom(categorieDTO.getNom());
            return new CategorieDTO(categorieRepository.save(oldCategorie));
        } else {
            throw new NotFoundException("Categorie not found");
        }
    }

    @Override
    @CacheEvict(value = "categorieIdCache", key = "#id")
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }
}
