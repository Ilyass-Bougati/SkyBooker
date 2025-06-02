package skybooker.server.service;

import org.springframework.http.ResponseEntity;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.entity.Client;

import java.security.Principal;

public interface ClientService extends CrudDTO<ClientDTO, Long> {
    void deleteByEmail(String email);
    ClientDTO findByEmail(String email);
    Client update(Client client, ClientDTO clientDTO);
    ResponseEntity<ClientDTO> register(RegisterRequestDTO registerRequestDTO);
    Boolean passagerAddedByClient(Long clientId, Long passagerId);
    Client getFromPrincipal(Principal principal);
}
