package skybooker.server.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.entity.*;
import skybooker.server.enums.EtatReservation;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.security.UserDetailsImpl;
import skybooker.server.repository.ClientRepository;
import skybooker.server.service.ClientService;
import skybooker.server.service.RoleService;
import skybooker.server.repository.ReservationRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@CacheConfig(cacheNames = {"clientCache"})
public class ClientServiceImpl implements ClientService {

    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final CategorieRepository categorieRepository;
    private final PassagerRepository passagerRepository;
    private final ClientRepository clientRepository;
    private final UserDetailsService userDetailsService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ReservationRepository reservationRepository;

    public ClientServiceImpl(ClientRepository clientRepository, UserDetailsService userDetailsService, RoleService roleService, CategorieRepository categorieRepository, PassagerRepository passagerRepository, PasswordEncoder passwordEncoder, ReservationRepository reservationRepository) {
        this.clientRepository = clientRepository;
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
        this.categorieRepository = categorieRepository;
        this.passagerRepository = passagerRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationRepository = reservationRepository;
    }

//    @Override
//    public List<ClientDTO> findAllDTO() {
//        return List.of();
//    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAllDTO() {
        return clientRepository.findAll()
                .stream()
                .map(client -> {
                    if (client.getReservations() != null) {
                        client.getReservations().size();
                    }
                    return new ClientDTO(client);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDTO findbyIdDTO(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with ID: " + id));

        return new ClientDTO(client);
    }

    @Transactional
    @Override
    public void cancelReservation(Long reservationId) {
        try {
            reservationRepository.modifyEtat(reservationId, EtatReservation.CANCELLED_BY_AIRLINE);
            logger.info("Reservation with ID {} has been cancelled.", reservationId);
        } catch (Exception e) {
            throw new NotFoundException("Failed to cancel reservation with ID: " + reservationId + ". Error: " + e.getMessage());
        }
    }

    /**
     * This function should never be used
     * @param clientDTO the client details
     * @return null in all cases
     */
    @Override
    @Deprecated
    public ClientDTO createDTO(ClientDTO clientDTO) {
        logger.warn("someone tried to create a client using createDTO");
        return null;
    }

    /**
     * This function should never be used
     * @param clientDTO the client details
     * @return null in all cases
     */
    @Override
    public ClientDTO updateDTO(ClientDTO clientDTO) {
        logger.warn("someone tried to create a client using updateDTO(ClientDTO)");
        return null;
    }


    @Caching(
            evict = {
                    @CacheEvict(key = "#email"),
                    @CacheEvict(value = "ClientEntityCache", key = "#email")
            },
            put = {
                    @CachePut(key = "#clientDTO.email")
            }
    )
    public ClientDTO updateDTO(ClientDTO clientDTO, String email) {
        Client client = clientRepository.findById(clientDTO.getId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        client.setAdresse(clientDTO.getAdresse());
        client.setTelephone(clientDTO.getTelephone());
        client.setEmail(clientDTO.getEmail());
        return new ClientDTO(clientRepository.save(client));
    }

    @Override
    @Transactional(readOnly = true)
    @CachePut(key = "#result.email")
    public Client findById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));
    }

    @Override
    @Deprecated
    public Client create(Client client) {
        logger.warn("Someone is trying to create a use with create(Client)");
        return null;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO findDTOById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.map(ClientDTO::new)
                .orElseThrow(() -> new NotFoundException("Client not found"));
    }


    @Override
    @Deprecated
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @CacheEvict(key = "#email")
    public void deleteByEmail(String email) {
        clientRepository.deleteByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#email")
    public ClientDTO findByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.map(ClientDTO::new).orElse(null);
    }



    @Override
    @CachePut(key = "#registerRequestDTO.email")
    public ResponseEntity<ClientDTO> register(RegisterRequestDTO registerRequestDTO) {
        Passager passager = registerRequestDTO.passager();
        passager.updateCategorie(categorieRepository);
        Client client = registerRequestDTO.client();
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        // giving USER_ROLE
        Role userRole = roleService.findById(1L);
        client.setRole(userRole);
        client.getPassagers().add(passager);

        // adding the passager to the client
        passager.setClient(client);

        clientRepository.save(client);
        passagerRepository.save(passager);
        return ResponseEntity.ok(new ClientDTO(client));
    }

    @Override
    public Boolean passagerAddedByClient(Long clientId, Long passagerId) {
        return clientRepository.passagerAddedByClient(clientId, passagerId);
    }

    @Override
    @Cacheable(value = "ClientEntityCache", key = "#principal.name")
    public Client getFromPrincipal(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        if (userDetails == null) {
            return null;
        } else {
            return userDetails.getClient();
        }
    }
}
