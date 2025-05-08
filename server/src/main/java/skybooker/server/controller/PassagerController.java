package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;
import skybooker.server.service.PassagerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passager")
public class PassagerController {

    @Autowired
    private PassagerService passagerService;

    @GetMapping("/")
    public ResponseEntity<List<Passager>> getAllPassager() {
        return ResponseEntity.ok(passagerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passager> getPassagerById(@PathVariable Long id) {
        Passager passager = passagerService.findById(id);
        if (passager == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(passager);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Passager> createPassager(@RequestBody @Valid PassagerDTO passagerDTO) {
        return ResponseEntity.ok(passagerService.createDTO(passagerDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Passager> updatePassager(@RequestBody @Valid PassagerDTO passagerDTO) {
        Passager passager = passagerService.updateDTO(passagerDTO);
        if (passager == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(passager);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePassager(@PathVariable Long id) {
        passagerService.deleteById(id);
    }
}
