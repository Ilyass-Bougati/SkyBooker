package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.service.AeroportService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/aeroport")
public class AeroportController {

    @Autowired
    private AeroportService aeroportService;

    @GetMapping("/")
    public ResponseEntity<List<Aeroport>> getAllAeroport() {
        return ResponseEntity.ok(aeroportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aeroport> getAeroportById(@PathVariable Long id) {
        Aeroport aeroport = aeroportService.findById(id);
        if (aeroport == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(aeroport);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Aeroport> createAeroport(@RequestBody @Valid AeroportDTO aeroportDTO) {
        return ResponseEntity.ok(aeroportService.createDTO(aeroportDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Aeroport> updateAeroport(@RequestBody @Valid AeroportDTO aeroportDTO) {
        Aeroport aeroport = aeroportService.updateDTO(aeroportDTO);
        if (aeroport == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(aeroport);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAeroport(@PathVariable Long id) {
        aeroportService.deleteById(id);
    }
}
