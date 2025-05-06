package skybooker.server.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.entity.Aeroport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvionDTO {
    private long id;

    @NotNull
    private String iataCode;

    @NotNull
    private String icaoCode;

    @NotNull
    private String model;

    @NotNull
    private double maxDistance;

    @NotNull
    private long companieAerienneId;
}
