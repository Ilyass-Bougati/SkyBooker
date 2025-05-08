package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.entity.Billet;
import skybooker.server.service.BilletService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/billet")
public class BilletController {

    @Autowired
    private BilletService billetService;

    @GetMapping("/")
    public ResponseEntity<List<Billet>> getAllBillet() {
        return ResponseEntity.ok(billetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billet> getBilletById(@PathVariable Long id) {
        Billet billet = billetService.findById(id);
        if (billet == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(billet);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Billet> createBillet(@RequestBody @Valid BilletDTO billetDTO) {
        return ResponseEntity.ok(billetService.createDTO(billetDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Billet> updateBillet(@RequestBody @Valid BilletDTO billetDTO) {
        Billet billet = billetService.updateDTO(billetDTO);
        if (billet == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(billet);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBillet(@PathVariable Long id) {
        billetService.deleteById(id);
    }
}
