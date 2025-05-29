package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.service.CategorieService;
import skybooker.server.service.PassagerService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository passagerRepository;
    private final CategorieService categorieService;

    public PassagerServiceImpl(PassagerRepository passagerRepository, CategorieService categorieService) {
        this.passagerRepository = passagerRepository;
        this.categorieService = categorieService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passager> findAll() {
        return passagerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Passager findById(Long id) {
        Optional<Passager> optionalPassager = passagerRepository.findById(id);
        return optionalPassager.orElse(null);
    }

    @Override
    public Passager create(Passager passager) {
        passager.lowerCase();
        return passagerRepository.save(passager);
    }

    @Override
    public Passager update(Passager passager) {
        Passager oldPassager = this.findById(passager.getId());
        if (oldPassager != null) {
            oldPassager.updateFields(passager);
            oldPassager.lowerCase();
            return passagerRepository.save(oldPassager);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        passagerRepository.deleteById(id);
    }

    @Override
    public void delete(Passager passager) {
        passagerRepository.delete(passager);
    }

    @Override
    public Passager createDTO(PassagerDTO passagerDTO) {
        Passager passager = new Passager(passagerDTO);
        passager.updateCategorie(categorieService);
        passager.lowerCase();
        return passagerRepository.save(passager);
    }

    @Override
    public Passager updateDTO(PassagerDTO passagerDTO) {
        Passager passager = findById(passagerDTO.getId());
        if (passager != null) {
            // updating the passager
            passager.setNom(passagerDTO.getNom());
            passager.setPrenom(passagerDTO.getPrenom());
            passager.setAge(passagerDTO.getAge());
            passager.setCIN(passagerDTO.getCIN());
            passager.updateCategorie(categorieService);
            passager.lowerCase();

            // saving the updates
            return passagerRepository.save(passager);
        } else {
            return null;
        }
    }
}
