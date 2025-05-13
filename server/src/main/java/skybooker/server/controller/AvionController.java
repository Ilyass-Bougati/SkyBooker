package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.entity.Avion;
import skybooker.server.service.AvionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/avion")
public class AvionController {

    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AvionDTO>> getAllAvion() {
        List<Avion> avions = avionService.findAll();
        List<AvionDTO> avionDTOs = avions.stream().map(AvionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(avionDTOs);
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
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<Avion> createAvion(@RequestBody @Valid AvionDTO avionDTO) {
        return ResponseEntity.ok(avionService.createDTO(avionDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<Avion> updateAvion(@RequestBody @Valid AvionDTO avionDTO) {
        Avion avion = avionService.updateDTO(avionDTO);
        if (avion == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(avion);
        }
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteAvion(@PathVariable Long id) {
        avionService.deleteById(id);
    }
}
