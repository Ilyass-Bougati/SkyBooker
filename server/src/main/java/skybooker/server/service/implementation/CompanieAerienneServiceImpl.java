package skybooker.server.service.implementation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.exception.NotFoundException;
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
    @Cacheable(value = "companieAerienneCache", key = "#id")
    public CompanieAerienneDTO findById(Long id) {
        Optional<CompanieAerienne> companieAerienne = companieAerienneRepository.findById(id);
        return companieAerienne
                .map(CompanieAerienneDTO::new)
                .orElseThrow(NotFoundException::new);
    }



    @Override
    @CacheEvict(value = "companieAerienneCache", key = "#id")
    public void deleteById(Long id) {
        companieAerienneRepository.deleteById(id);
    }


    @Override
    public List<CompanieAerienneDTO> findAll() {
        return companieAerienneRepository.findAll()
                .stream().map(CompanieAerienneDTO::new).toList();
    }

    @Override
    @CachePut(value = "companieAerienneCache", key = "#result.id")
    public CompanieAerienneDTO createDTO(CompanieAerienneDTO companieAerienneDTO) {
        return new CompanieAerienneDTO(companieAerienneRepository.save(new CompanieAerienne(companieAerienneDTO)));
    }

    @Override
    @CachePut(value = "companieAerienneCache", key = "#companieAerienneDTO.id")
    public CompanieAerienneDTO updateDTO(CompanieAerienneDTO companieAerienneDTO) {
        Optional<CompanieAerienne> companieAerienneOptional = companieAerienneRepository.findById(companieAerienneDTO.getId());
        if (companieAerienneOptional.isPresent()) {
            CompanieAerienne companieAerienne = companieAerienneOptional.get();
            // updating the airline
            companieAerienne.setNom(companieAerienneDTO.getNom());
            companieAerienne.setIcaoCode(companieAerienneDTO.getIcaoCode());
            companieAerienne.setIataCode(companieAerienneDTO.getIataCode());

            // saving the updated airline
            return new CompanieAerienneDTO(companieAerienneRepository.save(companieAerienne));
        } else {
            throw new NotFoundException("Companie arienne not found");
        }
    }
}
