package skybooker.server.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Vol;
import skybooker.server.enums.EtatVol;

import java.sql.Time;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolDTO {

    private long id;

    @NotNull
    private Date dateDepart;

    @NotNull
    private Time heureDepart;

    @NotNull
    private Date dateArrive;

    @NotNull
    private Time heureArrive;

    @NotNull
    private EtatVol etat;

    @Min(0)
    private double prix;

    @NotNull
    private long aeroportDepartId;

    @NotNull
    private long aeroportArriveId;

    @NotNull
    private long avionId;

    public VolDTO(Vol vol){
        this.id = vol.getId();
        this.dateDepart = vol.getDateDepart();
        this.heureDepart = vol.getHeureDepart();
        this.dateArrive = vol.getDateArrive();
        this.heureArrive = vol.getHeureArrive();
        this.etat = vol.getEtat();
        this.prix = vol.getPrix();
        this.aeroportArriveId = vol.getAeroportArrive().getId();
        this.aeroportDepartId = vol.getAeroportDepart().getId();
        this.avionId = vol.getAvion().getId();
    }
}
