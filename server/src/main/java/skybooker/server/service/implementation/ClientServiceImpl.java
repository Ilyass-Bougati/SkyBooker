package skybooker.server.service.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.entity.Client;
import skybooker.server.repository.ClientRepository;
import skybooker.server.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
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
}
