package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Capacite;
import skybooker.server.repository.CapaciteRepository;
import skybooker.server.service.CapaciteService;

import java.util.List;
import java.util.Optional;

@Service
public class CapaciteServiceImpl implements CapaciteService {

    @Autowired
    private CapaciteRepository capaciteRepository;

    @Override
    public List<Capacite> findAll() {
        return capaciteRepository.findAll();
    }

    @Override
    public Capacite findById(Long aLong) {
        Optional<Capacite> capacite = capaciteRepository.findById(aLong);
        return capacite.orElse(null);
    }

    @Override
    public Capacite create(Capacite entity) {
        return capaciteRepository.save(entity);
    }

    @Override
    public Capacite update(Capacite entity) {
        return capaciteRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        capaciteRepository.deleteById(aLong);
    }

    @Override
    public void delete(Capacite entity) {
        capaciteRepository.delete(entity);
    }
}
