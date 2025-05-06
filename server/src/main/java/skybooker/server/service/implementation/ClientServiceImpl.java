package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.entity.Client;
import skybooker.server.repository.ClientRepository;
import skybooker.server.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    @Override
    public Client create(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Client oldClient = this.findById(client.getId());
        if (oldClient != null) {
            oldClient.updateFields(client);
            return clientRepository.save(oldClient);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public void deleteByEmail(String email) {
        clientRepository.deleteByEmail(email);
    }

    @Override
    public Client findByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.orElse(null);
    }

    @Override
    public Client update(Client client, ClientDTO clientDTO) {
        client.updateFields(clientDTO);
        return clientRepository.save(client);
    }
}
