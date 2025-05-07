package skybooker.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanieAerienneDTO {
    private long id;
    private String nom;
    private String iataCode;
    private String icaoCode;
}
