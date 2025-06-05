package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.BilletDTO;
import skybooker.server.service.BilletService;

@RestController
@RequestMapping("/api/v1/billet")
public class BilletController {

    private final BilletService billetService;

    public BilletController(BilletService billetService) {
        this.billetService = billetService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<BilletDTO> getBilletById(@PathVariable Long id) {
        return ResponseEntity.ok(billetService.findDTOById(id));
    }

    @PostMapping("/")
    public ResponseEntity<BilletDTO> createBillet(@RequestBody @Valid BilletDTO billetDTO) {
        return ResponseEntity.ok(billetService.createDTO(billetDTO));

    }

    @PutMapping("/")
    public ResponseEntity<BilletDTO> updateBillet(@RequestBody @Valid BilletDTO billetDTO) {
        return ResponseEntity.ok(billetService.updateDTO(billetDTO));

    }

    @DeleteMapping("/{id}")
    public void deleteBillet(@PathVariable Long id) {
        billetService.deleteById(id);
    }
}
