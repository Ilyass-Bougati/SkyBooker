package skybooker.server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Avion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvionDTO {

    @JsonIgnore
    Logger logger = LoggerFactory.getLogger(AvionDTO.class);

    private Long id;

    @NotNull
    private String iataCode;

    @NotNull
    private String icaoCode;

    @NotNull
    private String model;

    @NotNull
    private double maxDistance;

    @NotNull
    private Long companieAerienneId;

    public AvionDTO(Avion avion) {
        this.id = avion.getId();
        this.iataCode = avion.getIataCode();
        this.icaoCode = avion.getIcaoCode();
        this.model = avion.getModel();
        this.maxDistance = avion.getMaxDistance();

        // checking if we have a companieAerienne
        if (avion.getCompanieAerienne() != null) {
            setCompanieAerienneId(avion.getCompanieAerienne().getId());
        } else {
            logger.warn("Companie aerienne for avion {} is null", getId());
            setCompanieAerienneId(null);
        }
    }
}
