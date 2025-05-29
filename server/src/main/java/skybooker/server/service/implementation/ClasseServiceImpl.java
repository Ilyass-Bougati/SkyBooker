package skybooker.server.service.implementation;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.entity.Classe;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.service.ClasseService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Classe> findAll() {
        return classeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "classeCache", key = "#id")
    public Classe findById(Long id) {
        Optional<Classe> classe = classeRepository.findById(id);
        return classe.orElse(null);
    }

    @Override
    @CachePut(value = "classeCache", key = "#classe.id")
    public Classe create(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    @CachePut(value = "classeCache", key = "#classe.id")
    public Classe update(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    @CacheEvict(value = "classeCache", key = "#id")
    public void deleteById(Long id) {
        classeRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "classeCache", key = "#classe.id")
    public void delete(Classe classe) {
        classeRepository.delete(classe);
    }
}
