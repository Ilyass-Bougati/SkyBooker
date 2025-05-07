package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.entity.Capacite;
import skybooker.server.repository.CapaciteRepository;
import skybooker.server.service.AvionService;
import skybooker.server.service.CapaciteService;
import skybooker.server.service.ClasseService;

import java.util.List;
import java.util.Optional;

@Service
public class CapaciteServiceImpl implements CapaciteService {

    @Autowired
    private CapaciteRepository capaciteRepository;
    @Autowired
    private AvionService avionService;
    @Autowired
    private ClasseService classeService;

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

    @Override
    public Capacite createDTO(CapaciteDTO capaciteDTO) {
        Capacite capacite = new Capacite(capaciteDTO);
        capacite.setAvion(avionService.findById(capaciteDTO.getAvionId()));
        capacite.setClasse(classeService.findById(capaciteDTO.getClasseId()));
        return capaciteRepository.save(capacite);
    }

    @Override
    public Capacite updateDTO(CapaciteDTO capaciteDTO) {
        Capacite capacite = findById(capaciteDTO.getId());
        if (capacite != null) {
            // modifying the capacity
            capacite.setClasse(classeService.findById(capaciteDTO.getClasseId()));
            capacite.setAvion(avionService.findById(capaciteDTO.getAvionId()));
            capacite.setBorneInf(capaciteDTO.getBorneInf());
            capacite.setBorneSup(capaciteDTO.getBorneSup());
            capacite.setCapacite(capaciteDTO.getCapacite());

            // saving the changes
            return capaciteRepository.save(capacite);
        } else {
            return null;
        }
    }
}
