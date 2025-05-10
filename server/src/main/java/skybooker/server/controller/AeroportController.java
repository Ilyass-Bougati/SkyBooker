package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.service.AeroportService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/aeroport")
public class AeroportController {

    @Autowired
    private AeroportService aeroportService;

    @GetMapping("/")
    public ResponseEntity<List<AeroportDTO>> getAllAeroport() {
        List<Aeroport> aeroports = aeroportService.findAll();
        List<AeroportDTO> aeroportDTOS = aeroports.stream().map(AeroportDTO::new).toList();
        return ResponseEntity.ok(aeroportDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AeroportDTO> getAeroportById(@PathVariable Long id) {
        Aeroport aeroport = aeroportService.findById(id);
        if (aeroport == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new AeroportDTO(aeroport));
        }
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<AeroportDTO> createAeroport(@RequestBody @Valid AeroportDTO aeroportDTO) {
        Aeroport aeroport = aeroportService.createDTO(aeroportDTO);
        return ResponseEntity.ok(new AeroportDTO(aeroport));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<AeroportDTO> updateAeroport(@RequestBody @Valid AeroportDTO aeroportDTO) {
        Aeroport aeroport = aeroportService.updateDTO(aeroportDTO);
        if (aeroport == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new AeroportDTO(aeroport));
        }
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteAeroport(@PathVariable Long id) {
        aeroportService.deleteById(id);
    }
}
