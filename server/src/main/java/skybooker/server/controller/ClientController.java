package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/passagers")
    public ResponseEntity<List<PassagerDTO>> getPassagers(Principal principal) {
        Client client = clientService.getFromPrincipal(principal);
        return ResponseEntity.ok(clientService.getPassagers(client.getId()));
    }

    @GetMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<ClientDTO> client(@PathVariable long id) {
        return ResponseEntity.ok(clientService.findDTOById(id));
    }

    @GetMapping("/")
    public ResponseEntity<ClientDTO> client(Principal principal) {
        Client client = clientService.getFromPrincipal(principal);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ClientDTO(client));
        }
    }

    @PutMapping("/")
    public ResponseEntity<ClientDTO> updateClient(Principal principal, @RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientService.getFromPrincipal(principal);
        if (client == null) {
            // shouldn't reveal that the client doesn't exist
            return ResponseEntity.badRequest().build();
        } else {
            // checking if the user is authorized to make the action
            clientDTO.setId(client.getId());
            return ResponseEntity.ok(clientService.updateDTO(clientDTO, client.getEmail()));
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
