package skybooker.server.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.BilletRepository;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.ClientRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.service.PassagerService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository passagerRepository;
    private final CategorieRepository categorieRepository;
    private final ClientRepository clientRepository;
    private final BilletRepository billetRepository;

    public PassagerServiceImpl(PassagerRepository passagerRepository, CategorieRepository categorieRepository, ClientRepository clientRepository, BilletRepository billetRepository) {
        this.passagerRepository = passagerRepository;
        this.categorieRepository = categorieRepository;
        this.clientRepository = clientRepository;
        this.billetRepository = billetRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PassagerDTO findDTOById(Long id) {
        Optional<Passager> optionalPassager = passagerRepository.findById(id);
        return optionalPassager
                .map(PassagerDTO::new)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PassagerDTO> findDTOsByIds(Set<Long> ids) {
        return passagerRepository.findAllById(ids).stream()
                .map(PassagerDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        passagerRepository.deleteById(id);
    }

    @Override
    public List<PassagerDTO> findAllDTO() {
        return List.of();
    }

    @Override
    public PassagerDTO createDTO(PassagerDTO passagerDTO) {
        Client client = clientRepository.findById(passagerDTO.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        Passager passager = new Passager(passagerDTO);
        passager.updateCategorie(categorieRepository);
        passager.setClient(client);
        passager.lowerCase();
        return new PassagerDTO(passagerRepository.save(passager));
    }

    @Override
    public PassagerDTO updateDTO(PassagerDTO passagerDTO) {
        Passager passager = passagerRepository.findById(passagerDTO.getId())
                .orElseThrow(() -> new NotFoundException("Passager not found"));

        // updating the passager
        passager.setNom(passagerDTO.getNom());
        passager.setPrenom(passagerDTO.getPrenom());
        passager.setDateOfBirth(passagerDTO.getDateOfBirth());
        passager.setCIN(passagerDTO.getCIN());
        passager.lowerCase();
        passager.updateCategorie(categorieRepository);

        // saving the updates
        return new PassagerDTO(passagerRepository.save(passager));
    }

    public boolean passagerAddedBy(Long clientId, Long passagerId) {
        return clientRepository.passagerAddedByClient(clientId, passagerId);
    }

    @Override
    public List<BilletDTO> getPassagerBillets(Long passagerId) {
        return billetRepository.passagerBillets(passagerId)
                .stream().map(BilletDTO::new).toList();
    }

    @Override
    public List<PassagerDTO> findByClientId(Long clientId) {
        return passagerRepository.findAllByClientId(clientId)
                .stream().map(PassagerDTO::new).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Passager> findAll() {
        return passagerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Passager findById(Long id) {
        Optional<Passager> passager = passagerRepository.findById(id);
        return passager.orElse(null);
    }

    @Override
    public Passager create(Passager passager) {
        PassagerDTO passagerDTO = new PassagerDTO(passager);
        passagerDTO = createDTO(passagerDTO);
        return passagerRepository.findById(passagerDTO.getId())
                .orElseThrow(() -> new NotFoundException("Passager not found"));
    }

    @Override
    public Passager update(Passager passager) {
        PassagerDTO passagerDTO = new PassagerDTO(passager);
        passagerDTO = updateDTO(passagerDTO);
        return passagerRepository.findById(passagerDTO.getId())
                .orElseThrow(() -> new NotFoundException("Passager not found"));
    }

    @Override
    public void delete(Passager passager) {
        deleteById(passager.getId());
    }
}
