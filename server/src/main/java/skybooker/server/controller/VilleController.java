package skybooker.server.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.VilleDTO;
import skybooker.server.entity.Ville;
import skybooker.server.service.VilleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ville")
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<VilleDTO>> getAllVille() {
        return ResponseEntity.ok(villeService.getAllVille());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getVilleById(@PathVariable Long id) {
        return ResponseEntity.ok(villeService.findById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VilleDTO> createVille(@RequestBody @Valid VilleDTO villeDTO) {
        return ResponseEntity.ok(villeService.createDTO(villeDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VilleDTO> updateVille(@RequestBody @Valid VilleDTO villeDTO) {
        return ResponseEntity.ok(villeService.updateDTO(villeDTO));

    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteVille(@PathVariable Long id) {
        villeService.deleteById(id);
    }
}
