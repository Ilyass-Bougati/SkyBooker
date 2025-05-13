package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Classe;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.service.ClasseService;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public List<Classe> findAll() {
        return classeRepository.findAll();
    }

    @Override
    public Classe findById(Long aLong) {
        Optional<Classe> classe = classeRepository.findById(aLong);
        return classe.orElse(null);
    }

    @Override
    public Classe create(Classe entity) {
        return classeRepository.save(entity);
    }

    @Override
    public Classe update(Classe entity) {
        return classeRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        classeRepository.deleteById(aLong);
    }

    @Override
    public void delete(Classe entity) {
        classeRepository.delete(entity);
    }
}
