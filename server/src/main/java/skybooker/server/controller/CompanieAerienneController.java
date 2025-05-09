package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.service.CompanieAerienneService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companie-aerienne")
public class CompanieAerienneController {

    @Autowired
    private CompanieAerienneService companieAerienneService;

    @GetMapping("/")
    public ResponseEntity<List<CompanieAerienneDTO>> getAllCompanieAerienne() {
        List<CompanieAerienne> companieAeriennes = companieAerienneService.findAll();
        List<CompanieAerienneDTO> dtos = companieAeriennes.stream().map(CompanieAerienneDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanieAerienneDTO> getCompanieAerienneById(@PathVariable Long id) {
        CompanieAerienne companieAerienne = companieAerienneService.findById(id);
        if (companieAerienne == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new CompanieAerienneDTO(companieAerienne));
        }
    }

    @PostMapping("/")
    public ResponseEntity<CompanieAerienneDTO> createCompanieAerienne(@RequestBody @Valid CompanieAerienneDTO companieAerienneDTO) {
        CompanieAerienne companieAerienne = companieAerienneService.createDTO(companieAerienneDTO);
        return ResponseEntity.ok(new CompanieAerienneDTO(companieAerienne));
    }

    @PutMapping("/")
    public ResponseEntity<CompanieAerienneDTO> updateCompanieAerienne(@RequestBody @Valid CompanieAerienneDTO companieAerienneDTO) {
        CompanieAerienne companieAerienne = companieAerienneService.updateDTO(companieAerienneDTO);
        if (companieAerienne == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new CompanieAerienneDTO(companieAerienne));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompanieAerienne(@PathVariable Long id) {
        companieAerienneService.deleteById(id);
    }
}
