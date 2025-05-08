package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.entity.Billet;
import skybooker.server.service.BilletService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/billet")
public class BilletController {

    @Autowired
    private BilletService billetService;

    @GetMapping("/")
    public ResponseEntity<List<BilletDTO>> getAllBillet() {
        List<Billet> billets = billetService.findAll();
        List<BilletDTO> billetDTOs = billets.stream().map(BilletDTO::new).toList();
        return ResponseEntity.ok(billetDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BilletDTO> getBilletById(@PathVariable Long id) {
        Billet billet = billetService.findById(id);
        if (billet == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new BilletDTO(billet));
        }
    }

    @PostMapping("/")
    public ResponseEntity<BilletDTO> createBillet(@RequestBody @Valid BilletDTO billetDTO) {
        Billet billet = billetService.createDTO(billetDTO);
        return ResponseEntity.ok(new BilletDTO(billet));
    }

    @PutMapping("/")
    public ResponseEntity<BilletDTO> updateBillet(@RequestBody @Valid BilletDTO billetDTO) {
        Billet billet = billetService.updateDTO(billetDTO);
        if (billet == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new BilletDTO(billet));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBillet(@PathVariable Long id) {
        billetService.deleteById(id);
    }
}
