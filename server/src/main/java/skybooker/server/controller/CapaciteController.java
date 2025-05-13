package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.entity.Capacite;
import skybooker.server.service.CapaciteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/capacite")
public class CapaciteController {

    private final CapaciteService capaciteService;

    public CapaciteController(CapaciteService capaciteService) {
        this.capaciteService = capaciteService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Capacite>> getAllCapacite() {
        return ResponseEntity.ok(capaciteService.findAll());
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
        Capacite capacite = capaciteService.createDTO(capaciteDTO);
        return ResponseEntity.ok(new CapaciteDTO(capacite));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CapaciteDTO> updateCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        Capacite capacite = capaciteService.updateDTO(capaciteDTO);
        if (capacite == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new CapaciteDTO(capacite));
        }
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteCapacite(@PathVariable Long id) {
        capaciteService.deleteById(id);
    }
}
