package skybooker.server.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skybooker.server.entity.Categorie;
import skybooker.server.enums.CategoryNameEnum;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategorieDTO {

    private long id;

    @NotNull
    private CategoryNameEnum nom;

    @Max(1)
    private double reduction;

    public CategorieDTO(Categorie categorie) {
        this.id = categorie.getId();
        this.nom = categorie.getNom();
        this.reduction = categorie.getReduction();
    }
}