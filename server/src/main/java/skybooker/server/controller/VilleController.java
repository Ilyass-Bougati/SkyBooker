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
    public ResponseEntity<VilleDTO> createVille(@RequestBody @Valid VilleDTO villeDTO) {
        Ville ville = villeService.createDTO(villeDTO);
        return ResponseEntity.ok(new VilleDTO(ville));
    }

    @PutMapping("/")
    public ResponseEntity<VilleDTO> updateVille(@RequestBody @Valid VilleDTO villeDTO) {
        Ville ville = villeService.updateDTO(villeDTO);
        if (ville == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new VilleDTO(ville));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteVille(@PathVariable Long id) {
        villeService.deleteById(id);
    }
}
