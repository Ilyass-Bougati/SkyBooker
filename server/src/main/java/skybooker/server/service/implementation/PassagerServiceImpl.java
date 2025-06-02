package skybooker.server.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.service.PassagerService;

import java.util.Optional;

@Service
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository passagerRepository;
    private final CategorieRepository categorieRepository;

    public PassagerServiceImpl(PassagerRepository passagerRepository, CategorieRepository categorieRepository) {
        this.passagerRepository = passagerRepository;
        this.categorieRepository = categorieRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PassagerDTO findById(Long id) {
        Optional<Passager> optionalPassager = passagerRepository.findById(id);
        return optionalPassager
                .map(PassagerDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        passagerRepository.deleteById(id);
    }

    @Override
    public PassagerDTO createDTO(PassagerDTO passagerDTO) {
        Passager passager = new Passager(passagerDTO);
        passager.updateCategorie(categorieRepository);
        passager.lowerCase();
        return new PassagerDTO(passagerRepository.save(passager));
    }

    @Override
    public PassagerDTO updateDTO(PassagerDTO passagerDTO) {
        Optional<Passager> passagerOptional = passagerRepository.findById(passagerDTO.getId());
        if (passagerOptional.isPresent()) {
            Passager passager = passagerOptional.get();
            // updating the passager

            passager.setNom(passagerDTO.getNom());
            passager.setPrenom(passagerDTO.getPrenom());
            passager.setAge(passagerDTO.getAge());
            passager.setCIN(passagerDTO.getCIN());
            passager.lowerCase();
            passager.updateCategorie(categorieRepository);

            // saving the updates
            return new PassagerDTO(passagerRepository.save(passager));
        } else {
            throw new NotFoundException("Passager not found");
        }
    }
}
