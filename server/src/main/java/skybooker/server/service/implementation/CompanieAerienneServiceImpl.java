package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.repository.CompanieAerienneRepository;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanieAerienneServiceImpl implements CompanieAerienneService {

    @Autowired
    private CompanieAerienneRepository companieAerienneRepository;

    @Override
    public List<CompanieAerienne> findAll() {
        return companieAerienneRepository.findAll();
    }

    @Override
    public CompanieAerienne findById(Long aLong) {
        Optional<CompanieAerienne> companieAerienne = companieAerienneRepository.findById(aLong);
        return companieAerienne.orElse(null);
    }

    @Override
    public CompanieAerienne create(CompanieAerienne entity) {
        return companieAerienneRepository.save(entity);
    }

    @Override
    public CompanieAerienne update(CompanieAerienne entity) {
        return companieAerienneRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        companieAerienneRepository.deleteById(aLong);
    }

    @Override
    public void delete(CompanieAerienne entity) {
        companieAerienneRepository.delete(entity);
    }
}
