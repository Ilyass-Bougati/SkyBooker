package skybooker.server.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Avion;

@Getter
@Setter
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

    public AvionDTO(Avion avion) {
        this.id = avion.getId();
        this.iataCode = avion.getIataCode();
        this.icaoCode = avion.getIcaoCode();
        this.model = avion.getModel();
        this.maxDistance = avion.getMaxDistance();
        setCompanieAerienneId(avion.getCompanieAerienne().getId());
    }
}
