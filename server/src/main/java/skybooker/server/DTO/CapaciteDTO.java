package skybooker.server.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Capacite;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CapaciteDTO {

    private long id;

    @NotNull
    private int capacite;

    @NotNull
    private int borneInf;

    @NotNull
    private int borneSup;

    @NotNull
    private long avionId;

    @NotNull
    private long classeId;

    public CapaciteDTO(Capacite capacite) {
        setCapacite(capacite.getCapacite());
        setBorneInf(capacite.getBorneInf());
        setBorneSup(capacite.getBorneSup());
        setAvionId(capacite.getAvion().getId());
        setClasseId(capacite.getClasse().getId());
    }
}
