package skybooker.server.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.service.CompanieAerienneService;


@RestController
@RequestMapping("/api/v1/companie-aerienne")
public class CompanieAerienneController {

    Logger logger = LoggerFactory.getLogger(CompanieAerienneController.class);
    private final CompanieAerienneService companieAerienneService;

    public CompanieAerienneController(CompanieAerienneService companieAerienneService) {
        this.companieAerienneService = companieAerienneService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanieAerienneDTO> getCompanieAerienneById(@PathVariable Long id) {
        return ResponseEntity.ok(companieAerienneService.findDTOById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CompanieAerienneDTO> createCompanieAerienne(@RequestBody @Valid CompanieAerienneDTO companieAerienneDTO) {
        return ResponseEntity.ok(companieAerienneService.createDTO(companieAerienneDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CompanieAerienneDTO> updateCompanieAerienne(@RequestBody @Valid CompanieAerienneDTO companieAerienneDTO) {
        return ResponseEntity.ok(companieAerienneService.updateDTO(companieAerienneDTO));
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteCompanieAerienne(@PathVariable Long id) {
        companieAerienneService.deleteById(id);
    }
}
