package skybooker.server.service.implementation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ClasseDTO;
import skybooker.server.entity.Classe;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.service.ClasseService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {

    Logger logger = LoggerFactory.getLogger(ClasseServiceImpl.class);
    private final ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allClassesCache", key = "'allClasses'")
    public List<ClasseDTO> findAllDTO() {
        List<Classe> classes = classeRepository.findAll();
        return classes.stream().map(ClasseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClasseDTO> findDTOsByIds(Set<Long> ids) {
        return classeRepository.findAllById(ids).stream()
                .map(ClasseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(put = {
            @CachePut(value = "classeCache", key = "#result.id")
    }, evict = {
            @CacheEvict(value = "allClassesCache", allEntries = true)
    })
    public ClasseDTO createDTO(ClasseDTO classeDTO) {
        Classe classe = new Classe(classeDTO);
        return new ClasseDTO(classeRepository.save(classe));
    }

    @Override
    @Caching(put = {
            @CachePut(value = "classeCache", key = "#classeDTO.id")
    }, evict = {
            @CacheEvict(value = "allClassesCache", allEntries = true)
    })
    public ClasseDTO updateDTO(ClasseDTO classeDTO) {
        Classe classe = classeRepository.findById(classeDTO.getId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));
        // updating the classe
        classe.setNom(classeDTO.getNom());
        classe.setPrixParKm(classeDTO.getPrixParKm());
        return new ClasseDTO(classeRepository.save(classe));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "classeCache", key = "#id")
    public ClasseDTO findDTOById(Long id) {
        Optional<Classe> classe = classeRepository.findById(id);
        return classe
                .map(ClasseDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "classeCache", key = "#id"),
            @CacheEvict(value = "allClassesCache", allEntries = true)
    })
    public void deleteById(Long id) {
        classeRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Classe> findAll() {
        return classeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Classe findById(Long id) {
        Optional<Classe> classe = classeRepository.findById(id);
        return classe.orElse(null);
    }

    @Override
    public Classe create(Classe classe) {
        ClasseDTO classeDTO = new ClasseDTO(classe);
        classeDTO = createDTO(classeDTO);
        return classeRepository.findById(classeDTO.getId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));
    }

    @Override
    public Classe update(Classe classe) {
        ClasseDTO classeDTO = new ClasseDTO(classe);
        classeDTO = updateDTO(classeDTO);
        return classeRepository.findById(classeDTO.getId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));
    }

    @Override
    public void delete(Classe classe) {
        deleteById(classe.getId());
    }
}
