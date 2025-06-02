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

    private final BilletService billetService;

    public BilletController(BilletService billetService) {
        this.billetService = billetService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<BilletDTO> getBilletById(@PathVariable Long id) {
        return ResponseEntity.ok(billetService.findById(id));
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
