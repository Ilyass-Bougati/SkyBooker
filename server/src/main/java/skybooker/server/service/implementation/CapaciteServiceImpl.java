package skybooker.server.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.entity.Avion;
import skybooker.server.entity.Capacite;
import skybooker.server.entity.Classe;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.AvionRepository;
import skybooker.server.repository.CapaciteRepository;
import skybooker.server.repository.ClasseRepository;
import skybooker.server.service.CapaciteService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CapaciteServiceImpl implements CapaciteService {

    private final CapaciteRepository capaciteRepository;
    private final AvionRepository avionRepository;
    private final ClasseRepository classeRepository;

    public CapaciteServiceImpl(CapaciteRepository capaciteRepository, AvionRepository avionRepository, ClasseRepository classeRepository) {
        this.capaciteRepository = capaciteRepository;
        this.avionRepository = avionRepository;
        this.classeRepository = classeRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public CapaciteDTO findById(Long id) {
        Optional<Capacite> capacite = capaciteRepository.findById(id);
        return capacite
                .map(CapaciteDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long aLong) {
        capaciteRepository.deleteById(aLong);
    }

    @Override
    public List<CapaciteDTO> findAll() {
        return List.of();
    }

    @Override
    public CapaciteDTO createDTO(CapaciteDTO capaciteDTO) {
        Avion avion = avionRepository.findById(capaciteDTO.getAvionId())
                .orElseThrow(() -> new NotFoundException("Avion not found"));
        Classe classe = classeRepository.findById(capaciteDTO.getClasseId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));

        Capacite capacite = new Capacite(capaciteDTO);
        capacite.setAvion(avion);
        capacite.setClasse(classe);
        return new CapaciteDTO(capaciteRepository.save(capacite));
    }

    @Override
    public CapaciteDTO updateDTO(CapaciteDTO capaciteDTO) {
        Capacite capacite = capaciteRepository.findById(capaciteDTO.getId())
                .orElseThrow(() -> new NotFoundException("Capacite not found"));
        Avion avion = avionRepository.findById(capaciteDTO.getAvionId())
                .orElseThrow(() -> new NotFoundException("Avion not found"));
        Classe classe = classeRepository.findById(capaciteDTO.getClasseId())
                .orElseThrow(() -> new NotFoundException("Classe not found"));

        // modifying the capacity
        capacite.setClasse(classe);
        capacite.setAvion(avion);
        capacite.setBorneInf(capaciteDTO.getBorneInf());
        capacite.setBorneSup(capaciteDTO.getBorneSup());
        capacite.setCapacite(capaciteDTO.getCapacite());

        // saving the changes
        return new CapaciteDTO(capaciteRepository.save(capacite));
    }
}
