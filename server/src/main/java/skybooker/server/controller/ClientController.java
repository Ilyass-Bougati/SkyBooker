package skybooker.server.controller;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.entity.Client;
import skybooker.server.repository.ClientRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.service.ClientService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> client(@PathVariable long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ClientDTO(client));
        }
    }

    @PutMapping("/")
    public ResponseEntity<ClientDTO> updateClient(Principal principal, @RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientService.findById(clientDTO.getId());
        if (client == null) {
            // shouldn't reveal that the client doesn't exist
            return ResponseEntity.badRequest().build();
        } else {
            // checking if the user is authorized to make the action
            // TODO : check if the user is admin
            if (principal.getName().equals(client.getEmail()))
            {
                client.updateFields(clientDTO);
                Client newClient = clientService.update(client);
                return ResponseEntity.ok(new ClientDTO(newClient));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(Principal principal, @PathVariable long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            // shouldn't reveal that the client doesn't exist
            return ResponseEntity.badRequest().build();
        } else {
            // checking if the user is authorized to make the action
            // TODO : check if the user is admin
            if (principal.getName().equals(client.getEmail()))
            {
                clientService.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }
}
