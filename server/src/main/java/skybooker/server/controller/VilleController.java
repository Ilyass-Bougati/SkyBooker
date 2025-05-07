package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.VilleDTO;
import skybooker.server.entity.Ville;
import skybooker.server.service.VilleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ville")
public class VilleController {

    @Autowired
    private VilleService villeService;

    @GetMapping("/")
    public ResponseEntity<List<Ville>> getAllVille() {
        return ResponseEntity.ok(villeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable Long id) {
        Ville ville = villeService.findById(id);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ville);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Ville> createVille(@RequestBody @Valid VilleDTO villeDTO) {
        return ResponseEntity.ok(villeService.createDTO(villeDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Ville> updateVille(@RequestBody @Valid VilleDTO villeDTO) {
        Ville ville = villeService.updateDTO(villeDTO);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ville);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteVille(@PathVariable Long id) {
        villeService.deleteById(id);
    }
}
