package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companie-aerienne")
public class CompanieAerienneController {

    @Autowired
    private CompanieAerienneService companieAerienneService;

    @GetMapping("/")
    public ResponseEntity<List<CompanieAerienne>> getAllCompanieAerienne() {
        return ResponseEntity.ok(companieAerienneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanieAerienne> getCompanieAerienneById(@PathVariable Long id) {
        CompanieAerienne companieAerienne = companieAerienneService.findById(id);
        if (companieAerienne == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(companieAerienne);
        }
    }

    @PostMapping("/")
    public ResponseEntity<CompanieAerienne> createCompanieAerienne(@RequestBody @Valid CompanieAerienneDTO companieAerienneDTO) {
        return ResponseEntity.ok(companieAerienneService.createDTO(companieAerienneDTO));
    }

    @PutMapping("/")
    public ResponseEntity<CompanieAerienne> updateCompanieAerienne(@RequestBody @Valid CompanieAerienneDTO companieAerienneDTO) {
        CompanieAerienne companieAerienne = companieAerienneService.updateDTO(companieAerienneDTO);
        if (companieAerienne == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(companieAerienne);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompanieAerienne(@PathVariable Long id) {
        companieAerienneService.deleteById(id);
    }
}
