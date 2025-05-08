package skybooker.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.entity.CompanieAerienne;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanieAerienneDTO {
    private long id;
    private String nom;
    private String iataCode;
    private String icaoCode;

    public CompanieAerienneDTO(CompanieAerienne companieAerienne) {
        setId(companieAerienne.getId());
        setNom(companieAerienne.getNom());
        setIataCode(companieAerienne.getIataCode());
        setIcaoCode(companieAerienne.getIcaoCode());
    }
}
