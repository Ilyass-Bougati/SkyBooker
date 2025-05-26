package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.entity.Capacite;
import skybooker.server.service.CapaciteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/capacite")
public class CapaciteController {

    private final CapaciteService capaciteService;

    public CapaciteController(CapaciteService capaciteService) {
        this.capaciteService = capaciteService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CapaciteDTO>> getAllCapacite() {
        List<Capacite> capacites = capaciteService.findAll();
        List<CapaciteDTO> capaciteDTOs = capacites.stream()
                .map(CapaciteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(capaciteDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapaciteDTO> getCapaciteById(@PathVariable Long id) {
        Capacite capacite = capaciteService.findById(id);
        if (capacite == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new CapaciteDTO(capacite));
        }
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CapaciteDTO> createCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        Capacite createdCapacite = capaciteService.createDTO(capaciteDTO);
        return ResponseEntity.ok(new CapaciteDTO(createdCapacite));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CapaciteDTO> updateCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        Capacite updatedCapacite = capaciteService.updateDTO(capaciteDTO);
        if (updatedCapacite == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new CapaciteDTO(updatedCapacite));
        }
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<Void> deleteCapacite(@PathVariable Long id) {
        Capacite capacite = capaciteService.findById(id);
        if (capacite == null) {
            return ResponseEntity.notFound().build();
        }

        capaciteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}