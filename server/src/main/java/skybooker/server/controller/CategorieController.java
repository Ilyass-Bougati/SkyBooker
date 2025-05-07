package skybooker.server.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.entity.Categorie;
import skybooker.server.service.CategorieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping("/")
    public ResponseEntity<List<Categorie>> getCategories() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategory(@PathVariable Long id) {
        Categorie categorie = categorieService.findById(id);
        if (categorie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categorie);
    }

    @PostMapping("/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Categorie> createCategory(@RequestBody @Valid Categorie categorie) {
        // Check if category with same name already exists
        if (categorieService.findByNom(categorie.getNom()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Categorie savedCategorie = categorieService.create(categorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategorie);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Categorie> updateCategory(@PathVariable Long id, @RequestBody @Valid Categorie categorie) {
        if (!id.equals(categorie.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Categorie existingCategorie = categorieService.findById(id);
        if (existingCategorie == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if changing the name would create a duplicate
        Categorie categorieWithSameName = categorieService.findByNom(categorie.getNom());
        if (categorieWithSameName != null && !Long.valueOf(categorieWithSameName.getId()).equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Categorie updatedCategorie = categorieService.update(categorie);
        return ResponseEntity.ok(updatedCategorie);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
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
