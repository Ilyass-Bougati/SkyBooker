package skybooker.server.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VilleDTO {
    private long id;

    @NotNull
    private String nom;

    @NotNull
    private String pays;
}
