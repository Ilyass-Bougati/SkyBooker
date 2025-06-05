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
    public CompanieAerienneDTO findDTOById(Long id) {
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
    public List<CompanieAerienneDTO> findAllDTO() {
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
        CompanieAerienne companieAerienne = companieAerienneRepository.findById(companieAerienneDTO.getId())
                .orElseThrow(() -> new NotFoundException("Companie arienne not found"));
        // updating the airline
        companieAerienne.setNom(companieAerienneDTO.getNom());
        companieAerienne.setIcaoCode(companieAerienneDTO.getIcaoCode());
        companieAerienne.setIataCode(companieAerienneDTO.getIataCode());
        // saving the updated airline
        return new CompanieAerienneDTO(companieAerienneRepository.save(companieAerienne));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanieAerienne> findAll() {
        return companieAerienneRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CompanieAerienne findById(Long id) {
        Optional<CompanieAerienne> companieAerienne = companieAerienneRepository.findById(id);
        return companieAerienne.orElse(null);
    }

    @Override
    public CompanieAerienne create(CompanieAerienne companieAerienne) {
        CompanieAerienneDTO companieAerienneDTO = new CompanieAerienneDTO(companieAerienne);
        companieAerienneDTO = createDTO(companieAerienneDTO);
        return companieAerienneRepository.findById(companieAerienneDTO.getId())
                .orElseThrow(() -> new NotFoundException("Companie arienne not found"));
    }

    @Override
    public CompanieAerienne update(CompanieAerienne companieAerienne) {
        CompanieAerienneDTO companieAerienneDTO = new CompanieAerienneDTO(companieAerienne);
        companieAerienneDTO = updateDTO(companieAerienneDTO);
        return companieAerienneRepository.findById(companieAerienneDTO.getId())
                .orElseThrow(() -> new NotFoundException("Companie arienne not found"));
    }

    @Override
    public void delete(CompanieAerienne companieAerienne) {
        deleteById(companieAerienne.getId());
    }
}
