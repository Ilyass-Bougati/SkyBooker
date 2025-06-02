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

    @GetMapping("/{id}")
    public ResponseEntity<CapaciteDTO> getCapaciteById(@PathVariable Long id) {
        return ResponseEntity.ok(capaciteService.findById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CapaciteDTO> createCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        return ResponseEntity.ok(capaciteService.createDTO(capaciteDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CapaciteDTO> updateCapacite(@RequestBody @Valid CapaciteDTO capaciteDTO) {
        return ResponseEntity.ok(capaciteService.updateDTO(capaciteDTO));

    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteCapacite(@PathVariable Long id) {
        capaciteService.deleteById(id);
    }
}