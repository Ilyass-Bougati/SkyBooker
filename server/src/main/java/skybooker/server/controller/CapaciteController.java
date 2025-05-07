package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.entity.Capacite;
import skybooker.server.service.CapaciteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/capacite")
public class CapaciteController {

    @Autowired
    private CapaciteService capaciteService;

    @GetMapping("/")
    public ResponseEntity<List<Capacite>> getAllCapacite() {
        return ResponseEntity.ok(capaciteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Capacite> getCapaciteById(@PathVariable Long id) {
        Capacite capacite = capaciteService.findById(id);
        if (capacite == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(capacite);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Capacite> createCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        return ResponseEntity.ok(capaciteService.createDTO(capaciteDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Capacite> updateCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        Capacite capacite = capaciteService.updateDTO(capaciteDTO);
        if (capacite == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(capacite);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCapacite(@PathVariable Long id) {
        capaciteService.deleteById(id);
    }
}
