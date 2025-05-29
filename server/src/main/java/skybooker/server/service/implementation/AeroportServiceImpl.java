package skybooker.server.service.implementation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.service.AeroportService;
import skybooker.server.service.VilleService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AeroportServiceImpl implements AeroportService {

    private final AeroportRepository aeroportRepository;

    public AeroportServiceImpl(AeroportRepository aeroportRepository, VilleService villeService) {
        this.aeroportRepository = aeroportRepository;
        this.villeService = villeService;
    }

    private final VilleService villeService;

    @Override
    @Transactional(readOnly = true)
    public List<Aeroport> findAll() {
        return aeroportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "aeroportCache", key = "#id")
    public Aeroport findById(Long id) {
        Optional<Aeroport> aeroport = aeroportRepository.findById(id);
        return aeroport.orElse(null);
    }

    @Override
    public Aeroport create(Aeroport aeroport) {
        return aeroportRepository.save(aeroport);
    }

    @Override
    public Aeroport createDTO(AeroportDTO aeroportDTO) {
        Aeroport aeroport = new Aeroport(aeroportDTO);
        aeroport.setVille(villeService.findById(aeroportDTO.getVilleId()));
        return aeroportRepository.save(aeroport);
    }

    @Override
    @CachePut(value = "aeroportCache", key = "#aeroport.id")
    public Aeroport update(Aeroport aeroport) {
        Aeroport newAeroport = findById(aeroport.getId());
        if (newAeroport != null) {
            // modifying the airport
            newAeroport.setVille(aeroport.getVille());
            newAeroport.setNom(aeroport.getNom());
            newAeroport.setIataCode(aeroport.getIataCode());
            newAeroport.setIcaoCode(aeroport.getIcaoCode());
            newAeroport.setLatitude(aeroport.getLatitude());
            newAeroport.setLongitude(aeroport.getLongitude());

            // saving the modifications
            return aeroportRepository.save(newAeroport);
        } else {
            return null;
        }
    }

    @Override
    @CachePut(value = "aeroportCache", key = "#aeroport.id")
    public Aeroport updateDTO(AeroportDTO aeroport) {
        Aeroport newAeroport = findById(aeroport.getId());
        if (newAeroport != null) {
            // modifying the airport
            newAeroport.setNom(aeroport.getNom());
            newAeroport.setIataCode(aeroport.getIataCode());
            newAeroport.setIcaoCode(aeroport.getIcaoCode());
            newAeroport.setLatitude(aeroport.getLatitude());
            newAeroport.setLongitude(aeroport.getLongitude());
            newAeroport.setVille(villeService.findById(aeroport.getVilleId()));

            // saving the modifications
            return aeroportRepository.save(newAeroport);
        } else {
            return null;
        }
    }

    @Override
    @CacheEvict(value = "aeroportCache", key = "#id")
    public void deleteById(Long id) {
        aeroportRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "aeroportCache", key = "#aeroport.id")
    public void delete(Aeroport aeroport) {
        aeroportRepository.delete(aeroport);
    }

}
