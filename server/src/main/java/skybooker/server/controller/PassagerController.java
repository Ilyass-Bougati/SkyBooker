package skybooker.server.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.UserDetailsImpl;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.service.PassagerService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/passager")
public class PassagerController {

    Logger logger = LoggerFactory.getLogger(PassagerController.class);

    private final PassagerService passagerService;
    private final UserDetailsService userDetailsService;

    public PassagerController(PassagerService passagerService, UserDetailsService userDetailsService) {
        this.passagerService = passagerService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PassagerDTO>> getAllPassager() {
        List<Passager> passagers = passagerService.findAll();
        List<PassagerDTO> passagerDTOs = passagers.stream().map(PassagerDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(passagerDTOs);
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
