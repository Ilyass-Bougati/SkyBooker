package skybooker.server.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skybooker.server.entity.Categorie;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategorieDTO {

    private long id;

    @NotNull
    private String nom;

    @Max(1)
    private double reduction;

    public CategorieDTO(Categorie categorie) {
        this.id = categorie.getId();
        this.nom = categorie.getNom();
        this.reduction = categorie.getReduction();
    }
}