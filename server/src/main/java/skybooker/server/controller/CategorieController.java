package skybooker.server.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CategorieDTO;
import skybooker.server.service.CategorieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategorieDTO>> getCategories() {
        return ResponseEntity.ok(categorieService.getAllCategories());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.findDTOById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CategorieDTO> createCategory(@RequestBody @Valid CategorieDTO categorieDTO) {
        return ResponseEntity.ok(categorieService.createDTO(categorieDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CategorieDTO> updateCategory(@RequestBody @Valid CategorieDTO categorieDTO) {
        return ResponseEntity.ok(categorieService.updateDTO(categorieDTO));
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteCategory(@PathVariable Long id) {
        categorieService.deleteById(id);
    }

}
