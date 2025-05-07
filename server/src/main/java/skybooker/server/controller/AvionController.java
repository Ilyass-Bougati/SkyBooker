package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;
import skybooker.server.service.AvionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/avion")
public class AvionController {

    @Autowired
    private AvionService avionService;

    @GetMapping("/")
    public ResponseEntity<List<Avion>> getAllAvion() {
        return ResponseEntity.ok(avionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avion> getAvionById(@PathVariable Long id) {
        Avion avion = avionService.findById(id);
        if (avion == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(avion);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Avion> createAvion(@RequestBody @Valid AvionDTO avionDTO) {
        return ResponseEntity.ok(avionService.createDTO(avionDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Avion> updateAvion(@RequestBody @Valid AvionDTO avionDTO) {
        Avion avion = avionService.updateDTO(avionDTO);
        if (avion == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(avion);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAvion(@PathVariable Long id) {
        avionService.deleteById(id);
    }
}
