package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Ville;
import skybooker.server.repository.VilleRepository;
import skybooker.server.service.VilleService;

import java.util.List;
import java.util.Optional;

@Service
public class VilleServiceImpl implements VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Override
    public List<Ville> findAll() {
        return villeRepository.findAll();
    }

    @Override
    public Ville findById(Long aLong) {
        Optional<Ville> ville = villeRepository.findById(aLong);
        return ville.orElse(null);
    }

    @Override
    public Ville create(Ville entity) {
        return villeRepository.save(entity);
    }

    @Override
    public Ville update(Ville entity) {
        return villeRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        villeRepository.deleteById(aLong);
    }

    @Override
    public void delete(Ville entity) {
        villeRepository.delete(entity);
    }
}
