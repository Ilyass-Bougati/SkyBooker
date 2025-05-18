package skybooker.server.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.CategorieDTO;
import skybooker.server.entity.Categorie;
import skybooker.server.service.CategorieService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategorieDTO>> getCategories() {
        List<Categorie> categories = categorieService.findAll();
        List<CategorieDTO> categoriesDTO = categories.stream()
                .map(CategorieDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriesDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategory(@PathVariable Long id) {
        Categorie categorie = categorieService.findById(id);
        if (categorie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CategorieDTO(categorie));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CategorieDTO> createCategory(@RequestBody @Valid CategorieDTO categorieDTO) {
        // Check if category with same name already exists
        if (categorieService.findByNom(categorieDTO.getNom()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Categorie categorieToSave = new Categorie();
        categorieToSave.setNom(categorieDTO.getNom());
        categorieToSave.setReduction(categorieDTO.getReduction());

        Categorie savedCategorie = categorieService.create(categorieToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CategorieDTO(savedCategorie));
    }

    @PutMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<CategorieDTO> updateCategory(@PathVariable Long id, @RequestBody @Valid CategorieDTO categorieDTO) {
        if (!id.equals(categorieDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Categorie existingCategorie = categorieService.findById(id);
        if (existingCategorie == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if changing the name would create a duplicate
        Categorie categorieWithSameName = categorieService.findByNom(categorieDTO.getNom());
        if (categorieWithSameName != null && !Long.valueOf(categorieWithSameName.getId()).equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        existingCategorie.setNom(categorieDTO.getNom());
        existingCategorie.setReduction(categorieDTO.getReduction());

        Categorie updatedCategorie = categorieService.update(existingCategorie);


        return ResponseEntity.ok(new CategorieDTO(updatedCategorie));
    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Categorie categorie = categorieService.findById(id);
        if (categorie == null) {
            return ResponseEntity.notFound().build();
        }

        // Here we need to check if category is used in any active reservations

        categorieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
