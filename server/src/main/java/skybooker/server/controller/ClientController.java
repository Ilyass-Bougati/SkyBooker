package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<ClientDTO> client(@PathVariable long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ClientDTO(client));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ClientDTO> client(Principal principal) {
        Client client = clientService.findByEmail(principal.getName());
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ClientDTO(client));
        }
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateClient(Principal principal, @RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientService.findByEmail(principal.getName());
        if (client == null) {
            // shouldn't reveal that the client doesn't exist
            return ResponseEntity.badRequest().build();
        } else {
            // checking if the user is authorized to make the action
            client.updateFields(clientDTO);
            clientService.update(client);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteClient(Principal principal) {
        if (principal != null) {
            clientService.deleteByEmail(principal.getName());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
