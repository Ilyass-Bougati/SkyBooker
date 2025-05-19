package skybooker.server.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.UserDetailsImpl;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Role;
import skybooker.server.repository.ClientRepository;
import skybooker.server.service.CategorieService;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;
import skybooker.server.service.RoleService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PassagerService passagerService;
    private final PasswordEncoder passwordEncoder;
    private final CategorieService categorieService;
    private final UserDetailsService userDetailsService;
    private final RoleService roleService;

    public ClientServiceImpl(ClientRepository clientRepository, PassagerService passagerService, PasswordEncoder passwordEncoder, CategorieService categorieService, UserDetailsService userDetailsService, RoleService roleService) {
        this.clientRepository = clientRepository;
        this.passagerService = passagerService;
        this.passwordEncoder = passwordEncoder;
        this.categorieService = categorieService;
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    @Override
    @Transactional
    public Client create(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setEmail(client.getEmail().toLowerCase());
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client update(Client client) {
        Client oldClient = findById(client.getId());
        if (oldClient != null) {
            oldClient.updateFields(client);
            client.setEmail(client.getEmail().toLowerCase());
            return clientRepository.save(oldClient);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        clientRepository.deleteByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.orElse(null);
    }

    @Override
    @Transactional
    public Client update(Client client, ClientDTO clientDTO) {
        client.updateFields(clientDTO);
        client.setEmail(client.getEmail().toLowerCase());
        return clientRepository.save(client);
    }

    // TODO : refactor this
    @Override
    @Transactional
    public ResponseEntity<ClientDTO> register(RegisterRequestDTO registerRequestDTO) {
        Passager passager = registerRequestDTO.passager();

        // giving the user the default category
        Categorie defaultCategorie = categorieService.findById(1L);
        if (defaultCategorie == null) {
            return ResponseEntity.badRequest().body(null);
        }
        passager.setCategorie(defaultCategorie);
        Client client = registerRequestDTO.client();

        // giving USER_ROLE
        Role userRole = roleService.findById(1L);
        client.setRole(userRole);
        client.getPassagers().add(passager);

        // adding the passager to the client
        passager.setClient(client);

        create(client);
        passagerService.create(passager);
        return ResponseEntity.ok(new ClientDTO(client));
    }

    @Override
    public Boolean passagerAddedByClient(Long clientId, Long passagerId) {
        return clientRepository.passagerAddedByClient(passagerId, clientId);
    }

    @Override
    public Client getFromPrincipal(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        if (userDetails == null) {
            return null;
        } else {
            return userDetails.getClient();
        }
    }
}
