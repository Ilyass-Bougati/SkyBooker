package skybooker.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import skybooker.server.DTO.CategorieDTO;
import skybooker.server.enums.CategoryNameEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private CategoryNameEnum nom;

    @Max(1)
    private double reduction;

    public Categorie(CategorieDTO categorieDTO) {
        this.nom = categorieDTO.getNom();
        this.reduction = categorieDTO.getReduction();
    }
}
