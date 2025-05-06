package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Avion;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.repository.AvionRepository;
import skybooker.server.service.AvionService;

import java.util.List;
import java.util.Optional;

@Service
public class AvionServiceImpl implements AvionService {

    @Autowired
    private AvionRepository avionRepository;

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
}
