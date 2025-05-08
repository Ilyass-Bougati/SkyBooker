package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.entity.Passager;
import skybooker.server.service.PassagerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/passager")
public class PassagerController {

    @Autowired
    private PassagerService passagerService;

    @GetMapping("/")
    public ResponseEntity<List<PassagerDTO>> getAllPassager() {
        List<Passager> passagers = passagerService.findAll();
        List<PassagerDTO> passagerDTOs = passagers.stream().map(PassagerDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(passagerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerDTO> getPassagerById(@PathVariable Long id) {
        Passager passager = passagerService.findById(id);
        if (passager == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new PassagerDTO(passager));
        }
    }

    @PostMapping("/")
    public ResponseEntity<PassagerDTO> createPassager(@RequestBody @Valid PassagerDTO passagerDTO) {
        Passager passager = passagerService.createDTO(passagerDTO);
        return ResponseEntity.ok(new PassagerDTO(passager));
    }

    @PutMapping("/")
    public ResponseEntity<PassagerDTO> updatePassager(@RequestBody @Valid PassagerDTO passagerDTO) {
        Passager passager = passagerService.updateDTO(passagerDTO);
        if (passager == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new PassagerDTO(passager));
        }
    }

    @DeleteMapping("/{id}")
    public void deletePassager(@PathVariable Long id) {
        passagerService.deleteById(id);
    }
}
