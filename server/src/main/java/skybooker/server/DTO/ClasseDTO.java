package skybooker.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skybooker.server.entity.Classe;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClasseDTO {
    private long id;
    private String nom;
    private int prixParKm;

    public ClasseDTO(Classe classe) {
        setNom(classe.getNom());
        setPrixParKm(classe.getPrixParKm());
        setId(classe.getId());
    }
}
