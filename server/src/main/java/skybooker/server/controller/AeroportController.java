package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.service.AeroportService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/aeroport")
public class AeroportController {

    private final AeroportService aeroportService;

    public AeroportController(AeroportService aeroportService) {
        this.aeroportService = aeroportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AeroportDTO> getAeroportById(@PathVariable Long id) {
        return ResponseEntity.ok(aeroportService.findById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<AeroportDTO> createAeroport(@RequestBody @Valid AeroportDTO aeroportDTO) {
        return ResponseEntity.ok(aeroportService.createDTO(aeroportDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<AeroportDTO> updateAeroport(@RequestBody @Valid AeroportDTO aeroportDTO) {
        return ResponseEntity.ok(aeroportService.updateDTO(aeroportDTO));
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteAeroport(@PathVariable Long id) {
        aeroportService.deleteById(id);
    }
}
