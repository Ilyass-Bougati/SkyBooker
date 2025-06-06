package skybooker.server.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/passager")
public class PassagerController {

    private final ClientService clientService;
    Logger logger = LoggerFactory.getLogger(PassagerController.class);

    private final PassagerService passagerService;

    public PassagerController(PassagerService passagerService, ClientService clientService) {
        this.passagerService = passagerService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PassagerDTO>> getAllPassager(Principal principal) {
        Client client = clientService.getFromPrincipal(principal);
        return ResponseEntity.ok(passagerService.findByClientId(client.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerDTO> getPassagerById(Principal principal, @PathVariable Long id) {
        // checking if the passager was originally added by the client
        Client client = clientService.getFromPrincipal(principal);

        if (passagerService.passagerAddedBy(client.getId(), id) || client.isAdmin()) {
            PassagerDTO passager = passagerService.findDTOById(id);
            return ResponseEntity.ok(passager);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<PassagerDTO> createPassager(Principal principal, @RequestBody @Valid PassagerDTO passagerDTO) {
        Client client = clientService.getFromPrincipal(principal);
        passagerDTO.setClientId(client.getId());
        PassagerDTO passager = passagerService.createDTO(passagerDTO);
        return ResponseEntity.ok(passager);
    }

    @PutMapping("/")
    public ResponseEntity<PassagerDTO> updatePassager(Principal principal, @RequestBody @Valid PassagerDTO passagerDTO) {
        Client client = clientService.getFromPrincipal(principal);
        if (clientService.passagerAddedByClient(client.getId(), passagerDTO.getId())) {
            passagerDTO.setClientId(client.getId());
            return ResponseEntity.ok(passagerService.updateDTO(passagerDTO));
        } else {
            logger.trace("Can't do");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePassager(@PathVariable Long id) {
        passagerService.deleteById(id);
    }
}
