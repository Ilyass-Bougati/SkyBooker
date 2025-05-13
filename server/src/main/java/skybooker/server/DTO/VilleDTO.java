package skybooker.server.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.entity.Ville;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VilleDTO {
    private long id;

    @NotNull
    private String nom;

    @NotNull
    private String pays;

    public VilleDTO(Ville ville) {
        setId(ville.getId());
        setNom(ville.getNom());
        setPays(ville.getPays());
    }
}
