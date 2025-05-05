package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Passager;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.service.PassagerService;

import java.util.List;
import java.util.Optional;

@Service
public class PassagerServiceImpl implements PassagerService {

    @Autowired
    PassagerRepository passagerRepository;

    @Override
    public List<Passager> findAll() {
        return passagerRepository.findAll();
    }

    @Override
    public Passager findById(Long id) {
        Optional<Passager> optionalPassager = passagerRepository.findById(id);
        return optionalPassager.orElse(null);
    }

    @Override
    public Passager create(Passager passager) {
        return passagerRepository.save(passager);
    }

    @Override
    public Passager update(Passager passager) {
        Passager oldPassager = this.findById(passager.getId());
        if (oldPassager != null) {
            oldPassager.updateFields(passager);
            return passagerRepository.save(oldPassager);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        passagerRepository.deleteById(id);
    }

    @Override
    public void delete(Passager passager) {
        passagerRepository.delete(passager);
    }
}
