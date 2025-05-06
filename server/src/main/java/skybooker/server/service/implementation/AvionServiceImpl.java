package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;
import skybooker.server.repository.AvionRepository;
import skybooker.server.service.AvionService;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;
import java.util.Optional;

@Service
public class AvionServiceImpl implements AvionService {

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private CompanieAerienneService companieAerienneService;

    @Override
    public List<Avion> findAll() {
        return avionRepository.findAll();
    }

    @Override
    public Avion findById(Long aLong) {
        Optional<Avion> avion = avionRepository.findById(aLong);
        return avion.orElse(null);
    }

    @Override
    public Avion create(Avion entity) {
        return avionRepository.save(entity);
    }

    @Override
    public Avion update(Avion entity) {
        return avionRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        avionRepository.deleteById(aLong);
    }

    @Override
    public void delete(Avion entity) {
        avionRepository.delete(entity);
    }

    @Override
    public Avion createDTO(AvionDTO avionDTO) {
        Avion avion = new Avion(avionDTO);
        avion.setCompanieAerienne(companieAerienneService.findById(avionDTO.getCompanieAerienneId()));
        return avionRepository.save(avion);
    }

    @Override
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
