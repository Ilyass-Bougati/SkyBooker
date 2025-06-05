package skybooker.server.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.service.AvionService;

@RestController
@RequestMapping("/api/v1/avion")
public class AvionController {

    Logger logger = LoggerFactory.getLogger(AvionController.class);
    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvionDTO> getAvionById(@PathVariable Long id) {
        return ResponseEntity.ok(avionService.findDTOById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<AvionDTO> createAvion(@RequestBody @Valid AvionDTO avionDTO) {
        return ResponseEntity.ok(avionService.createDTO(avionDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<AvionDTO> updateAvion(@RequestBody @Valid AvionDTO avionDTO) {
        return ResponseEntity.ok(avionService.updateDTO(avionDTO));
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteAvion(@PathVariable Long id) {
        avionService.deleteById(id);
    }
}
