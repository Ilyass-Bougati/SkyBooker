package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Billet;
import skybooker.server.repository.BilletRepository;
import skybooker.server.service.BilletService;

import java.util.List;
import java.util.Optional;

@Service
public class BilletServiceImpl implements BilletService {

    @Autowired
    private BilletRepository billetRepository;

    @Override
    public List<Billet> findAll() {
        return billetRepository.findAll();
    }

    @Override
    public Billet findById(Long aLong) {
        Optional<Billet> billet = billetRepository.findById(aLong);
        return billet.orElse(null);
    }

    @Override
    public Billet create(Billet entity) {
        return billetRepository.save(entity);
    }

    @Override
    public Billet update(Billet entity) {
        return billetRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        billetRepository.deleteById(aLong);
    }

    @Override
    public void delete(Billet entity) {
        billetRepository.delete(entity);
    }
}
