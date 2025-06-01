package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.repository.CompanieAerienneRepository;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanieAerienneServiceImpl implements CompanieAerienneService {

    private final CompanieAerienneRepository companieAerienneRepository;

    public CompanieAerienneServiceImpl(CompanieAerienneRepository companieAerienneRepository) {
        this.companieAerienneRepository = companieAerienneRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanieAerienne> findAll() {
        return companieAerienneRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "companieAerienneCache", key = "#id")
    public CompanieAerienne findById(Long id) {
        Optional<CompanieAerienne> companieAerienne = companieAerienneRepository.findById(id);
        return companieAerienne.orElse(null);
    }

    @Override
    @CachePut(value = "companieAerienneCache", key = "#companieAerienne.id")
    public CompanieAerienne create(CompanieAerienne companieAerienne) {
        return companieAerienneRepository.save(companieAerienne);
    }

    @Override
    @CachePut(value = "companieAerienneCache", key = "#companieAerienne.id")
    public CompanieAerienne update(CompanieAerienne companieAerienne) {
        return companieAerienneRepository.save(companieAerienne);
    }

    @Override
    @CacheEvict(value = "companieAerienneCache", key = "#id")
    public void deleteById(Long id) {
        companieAerienneRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "companieAerienneCache", key = "#companieAerienne.id")
    public void delete(CompanieAerienne companieAerienne) {
        companieAerienneRepository.delete(companieAerienne);
    }

    @Override
    @CachePut(value = "companieAerienneCache", key = "#result.id")
    public CompanieAerienne createDTO(CompanieAerienneDTO companieAerienneDTO) {
        return create(new CompanieAerienne(companieAerienneDTO));
    }

    @Override
    @CachePut(value = "companieAerienneCache", key = "#companieAerienneDTO.id")
    public CompanieAerienne updateDTO(CompanieAerienneDTO companieAerienneDTO) {
        CompanieAerienne companieAerienne = findById(companieAerienneDTO.getId());
        if (companieAerienne != null) {
            // updating the airline
            companieAerienne.setNom(companieAerienneDTO.getNom());
            companieAerienne.setIcaoCode(companieAerienneDTO.getIcaoCode());
            companieAerienne.setIataCode(companieAerienneDTO.getIataCode());

            // saving the updated airline
            return companieAerienneRepository.save(companieAerienne);
        } else {
            return null;
        }
    }
}
