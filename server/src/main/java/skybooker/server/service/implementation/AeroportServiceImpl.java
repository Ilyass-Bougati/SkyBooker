package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Aeroport;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.service.AeroportService;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportServiceImpl implements AeroportService {

    @Autowired
    private AeroportRepository aeroportRepository;

    @Override
    public List<Aeroport> findAll() {
        return aeroportRepository.findAll();
    }

    @Override
    public Aeroport findById(Long aLong) {
        Optional<Aeroport> aeroport = aeroportRepository.findById(aLong);
        return aeroport.orElse(null);
    }

    @Override
    public Aeroport create(Aeroport entity) {
        return aeroportRepository.save(entity);
    }

    @Override
    public Aeroport update(Aeroport entity) {
        return aeroportRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        aeroportRepository.deleteById(aLong);
    }

    @Override
    public void delete(Aeroport entity) {
        aeroportRepository.delete(entity);
    }
}
