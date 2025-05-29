package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@Transactional
public class ClientServiceImpl implements ClientService {

    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

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
    @Cacheable(value = "clientIdCache", key = "#id")
    public Client findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    @Override
    @CachePut(value = "clientEmailCache", key = "#client.email")
    public Client create(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setEmail(client.getEmail().toLowerCase());
        return clientRepository.save(client);
    }

    @Override
    @CachePut(value = "clientEmailCache", key = "#client.email")
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
    @CacheEvict(value = "clientIdCache", key = "#id")
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Deprecated
    public void delete(Client client) {
        logger.warn("delete method does nothing, use deleteByEmail");
        return;
    }

    @Override
    @CacheEvict(value = "clientEmailCache", key = "#email")
    public void deleteByEmail(String email) {
        clientRepository.deleteByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "clientEmailCache", key = "#email")
    public ClientDTO findByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.map(ClientDTO::new).orElse(null);
    }

    @Override
    @CachePut(value = "clientEmailCache", key = "#clientDTO.email")
    public Client update(Client client, ClientDTO clientDTO) {
        client.updateFields(clientDTO);
        client.setEmail(client.getEmail().toLowerCase());
        return clientRepository.save(client);
    }

    @Override
    @CachePut(value = "clientEmailCache", key = "#registerRequestDTO.email")
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
    @Cacheable(value = "clientIdCache", key = "#principal.name")
    public Client getFromPrincipal(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        if (userDetails == null) {
            return null;
        } else {
            return userDetails.getClient();
        }
    }
}
