package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;
import skybooker.server.repository.AvionRepository;
import skybooker.server.service.AvionService;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvionServiceImpl implements AvionService {

    private final AvionRepository avionRepository;
    private final CompanieAerienneService companieAerienneService;

    public AvionServiceImpl(AvionRepository avionRepository, CompanieAerienneService companieAerienneService) {
        this.avionRepository = avionRepository;
        this.companieAerienneService = companieAerienneService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Avion> findAll() {
        return avionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "avionCache", key = "#id")
    public Avion findById(Long id) {
        Optional<Avion> avion = avionRepository.findById(id);
        return avion.orElse(null);
    }

    @Override
    @CachePut(value = "avionCache", key = "#avion.id")
    public Avion create(Avion avion) {
        return avionRepository.save(avion);
    }

    @Override
    @CachePut(value = "avionCache", key = "#avion.id")
    public Avion update(Avion avion) {
        return avionRepository.save(avion);
    }

    @Override
    @CacheEvict(value = "avionCache", key = "#id")
    public void deleteById(Long id) {
        avionRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "avionCache", key = "#avion.id")
    public void delete(Avion avion) {
        avionRepository.delete(avion);
    }

    @Override
    public Avion createDTO(AvionDTO avionDTO) {
        Avion avion = new Avion(avionDTO);
        avion.setCompanieAerienne(companieAerienneService.findById(avionDTO.getCompanieAerienneId()));
        return avionRepository.save(avion);
    }

    @Override
    @CachePut(value = "avionCache", key = "#avionDTO.id")
    public Avion updateDTO(AvionDTO avionDTO) {
        Avion avion = findById(avionDTO.getId());
        if (avion != null) {
            // modifying the avion
            avion.setModel(avionDTO.getModel());
            avion.setIcaoCode(avionDTO.getIcaoCode());
            avion.setIataCode(avionDTO.getIataCode());
            avion.setMaxDistance(avionDTO.getMaxDistance());
            avion.setCompanieAerienne(companieAerienneService.findById(avionDTO.getCompanieAerienneId()));

            // saving the avion
            return avionRepository.save(avion);
        } else{
            return null;
        }

    }
}
