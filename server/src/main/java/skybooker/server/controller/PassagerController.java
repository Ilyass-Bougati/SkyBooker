package skybooker.server.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.UserDetailsImpl;
import skybooker.server.entity.Billet;
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
    private final UserDetailsService userDetailsService;

    public PassagerController(PassagerService passagerService, UserDetailsService userDetailsService, ClientService clientService) {
        this.passagerService = passagerService;
        this.userDetailsService = userDetailsService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PassagerDTO>> getAllPassager(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        Set<Passager> passagers = userDetails.getClient().getPassagers();
        List<PassagerDTO> passagerDTOs = passagers.stream().map(PassagerDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(passagerDTOs);
    }

    // TODO : This definitely needs to be refactored
    @GetMapping("/{passagerId}/billets")
    public ResponseEntity<List<BilletDTO>> getAllPassagersBillets(Principal principal, @PathVariable Long passagerId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        Client client = userDetails.getClient();
        if (clientService.passagerAddedByClient(client.getId(), passagerId) || client.isAdmin()) {
            Passager passager = passagerService.findById(passagerId);
            if (passager != null) {
                Set<Billet> billets =  passager.getBillets();
                List<BilletDTO> billetDTOS = billets.stream().map(BilletDTO::new).toList();
                return ResponseEntity.ok(billetDTOS);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerDTO> getPassagerById(Principal principal, @PathVariable Long id) {
        Passager passager = passagerService.findById(id);
        if (passager == null) {
            return ResponseEntity.notFound().build();
        } else {
            // checking if the passager was originally added by the client
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
            Client client = userDetails.getClient();


            if (!client.getPassagers().stream().filter(p -> p.getId() == id).toList().isEmpty() || client.isAdmin()) {
                return ResponseEntity.ok(new PassagerDTO(passager));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @PostMapping("/")
    public ResponseEntity<PassagerDTO> createPassager(@RequestBody @Valid PassagerDTO passagerDTO) {
        Passager passager = passagerService.createDTO(passagerDTO);
        return ResponseEntity.ok(new PassagerDTO(passager));
    }

    @PutMapping("/")
    public ResponseEntity<PassagerDTO> updatePassager(@RequestBody @Valid PassagerDTO passagerDTO) {
        Passager passager = passagerService.updateDTO(passagerDTO);
        if (passager == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new PassagerDTO(passager));
        }
    }

    @DeleteMapping("/{id}")
    public void deletePassager(@PathVariable Long id) {
        passagerService.deleteById(id);
    }
}
