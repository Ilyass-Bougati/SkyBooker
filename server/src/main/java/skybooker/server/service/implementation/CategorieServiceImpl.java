package skybooker.server.service.implementation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.CategorieDTO;
import skybooker.server.entity.Categorie;
import skybooker.server.exception.NotFoundException;
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
    @Cacheable(value = "allCategoriesCache", key = "'allCategories'")
    public List<CategorieDTO> findAllDTO() {
        return categorieRepository.findAll()
                .stream().map(CategorieDTO::new).toList();
    }

    @Override
    @Caching(put = {
            @CachePut(value = "categorieIdCache", key = "#result.id"),
            @CachePut(value = "categorieNameCache", key = "#result.nom")
    }, evict = {
            @CacheEvict(value = "allCategoriesCache", allEntries = true)
    })
    public CategorieDTO createDTO(CategorieDTO categorieDTO) {
        Categorie categorie = new Categorie(categorieDTO);
        return new CategorieDTO(categorieRepository.save(categorie));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categorieIdCache", key = "#id")
    public CategorieDTO findDTOById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie
                .map(CategorieDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "categorieNameCache", key = "#name")
    public CategorieDTO findByNom(String name) {
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
    @Caching(put = {
            @CachePut(value = "categorieIdCache", key = "#categorieDTO.id")
    }, evict = {
            @CacheEvict(value = "allCategoriesCache", allEntries = true)
    })
    public CategorieDTO updateDTO(CategorieDTO categorieDTO) {
        Categorie categorie = categorieRepository.findById(categorieDTO.getId())
                .orElseThrow(() -> new NotFoundException("Categorie not found"));
        // updating the categorie
        categorie.setReduction(categorieDTO.getReduction());
        categorie.setNom(categorieDTO.getNom());
        return new CategorieDTO(categorieRepository.save(categorie));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "categorieIdCache", key = "#id"),
            @CacheEvict(value = "categorieNameCache", key = "#name", beforeInvocation = true, condition = "#name != null"),
            @CacheEvict(value = "allCategoriesCache", allEntries = true)
    })
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Categorie findById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie.orElse(null);
    }

    @Override
    public Categorie create(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO(categorie);
        categorieDTO = createDTO(categorieDTO);
        return categorieRepository.findById(categorieDTO.getId())
                .orElseThrow(() -> new NotFoundException("Categorie not found"));
    }

    @Override
    public Categorie update(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO(categorie);
        categorieDTO = updateDTO(categorieDTO);
        return categorieRepository.findById(categorieDTO.getId())
                .orElseThrow(() -> new NotFoundException("Categorie not found"));
    }

    @Override
    public void delete(Categorie categorie) {
        deleteById(categorie.getId());
    }
}
