package skybooker.server.service.implementation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Ville;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.repository.VilleRepository;
import skybooker.server.service.AeroportService;
import skybooker.server.service.VilleService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AeroportServiceImpl implements AeroportService {

    private final AeroportRepository aeroportRepository;
    private final VilleRepository villeRepository;

    public AeroportServiceImpl(AeroportRepository aeroportRepository, VilleRepository villeRepository) {
        this.aeroportRepository = aeroportRepository;
        this.villeRepository = villeRepository;
    }



    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "aeroportCache", key = "#id")
    public AeroportDTO findById(Long id) {
        Optional<Aeroport> aeroport = aeroportRepository.findById(id);
        return aeroport
                .map(AeroportDTO::new)
                .orElseThrow(NotFoundException::new);
    }


    @Override
    public List<AeroportDTO> findAll() {
        return aeroportRepository.findAll()
                .stream().map(AeroportDTO::new).toList();
    }

    @Override
    @CachePut(value = "aeroportCache", key = "#result.id")
    public AeroportDTO createDTO(AeroportDTO aeroportDTO) {
        Optional<Ville> villeOptional = villeRepository.findById(aeroportDTO.getVilleId());
        if (villeOptional.isPresent()) {
            Aeroport aeroport = new Aeroport(aeroportDTO);
            aeroport.setVille(villeOptional.get());
            return new AeroportDTO(aeroportRepository.save(aeroport));
        } else {
            throw new NotFoundException("Ville not found");
        }
    }

    @Override
    @CachePut(value = "aeroportCache", key = "#aeroportDTO.id")
    public AeroportDTO updateDTO(AeroportDTO aeroportDTO) {
        Optional<Aeroport> newAeroportOpt = aeroportRepository.findById(aeroportDTO.getId());
        if (newAeroportOpt.isEmpty()) {
            throw new NotFoundException("Aeroport not found");
        } else {
            Aeroport newAeroport = newAeroportOpt.get();
            Optional<Ville> villeOptional = villeRepository.findById(aeroportDTO.getVilleId());
            if (villeOptional.isEmpty()) {
                throw new NotFoundException("Ville not found");
            }

            // modifying the airport
            newAeroport.setNom(aeroportDTO.getNom());
            newAeroport.setIataCode(aeroportDTO.getIataCode());
            newAeroport.setIcaoCode(aeroportDTO.getIcaoCode());
            newAeroport.setLatitude(aeroportDTO.getLatitude());
            newAeroport.setLongitude(aeroportDTO.getLongitude());
            newAeroport.setVille(villeOptional.get());

            // saving the modifications
            return new AeroportDTO(aeroportRepository.save(newAeroport));
        }
    }

    @Override
    @CacheEvict(value = "aeroportCache", key = "#id")
    public void deleteById(Long id) {
        aeroportRepository.deleteById(id);
    }
}
