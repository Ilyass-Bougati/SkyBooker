package skybooker.server.DTO;

import lombok.*;
import skybooker.server.entity.CompanieAerienne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanieAerienneDTO {
    private Long id;
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
