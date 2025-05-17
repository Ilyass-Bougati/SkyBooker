package skybooker.server.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Ville;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VilleDTO {
    private long id;

    @NotNull
    @Column(unique = true)
    private String nom;

    @NotNull
    private String pays;

    public VilleDTO(Ville ville) {
        setId(ville.getId());
        setNom(ville.getNom());
        setPays(ville.getPays());
    }
}
