package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Ville> villes = villeService.findAll();
        List<VilleDTO> villeDTOs = villes.stream().map(VilleDTO::new).toList();
        return ResponseEntity.ok(villeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getVilleById(@PathVariable Long id) {
        Ville ville = villeService.findById(id);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VilleDTO(ville));
        }
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VilleDTO> createVille(@RequestBody @Valid VilleDTO villeDTO) {
        Ville ville = villeService.createDTO(villeDTO);
        return ResponseEntity.ok(new VilleDTO(ville));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VilleDTO> updateVille(@RequestBody @Valid VilleDTO villeDTO) {
        Ville ville = villeService.updateDTO(villeDTO);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VilleDTO(ville));
        }
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteVille(@PathVariable Long id) {
        villeService.deleteById(id);
    }
}
