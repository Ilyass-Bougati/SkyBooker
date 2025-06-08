package skybooker.server.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Billet;
import skybooker.server.entity.Classe;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;
import skybooker.server.enums.EtatBillet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BilletDTO {
    private long id;
    private int siege;

    @NotNull
    private long classeId;

    @NotNull
    private long passagerId;

    @NotNull
    private long reservationId;

    private EtatBillet etat;

    public BilletDTO(Billet billet) {
        setId(billet.getId());
        setSiege(billet.getSiege());
        setClasseId(billet.getClasse().getId());
        setPassagerId(billet.getPassager().getId());
        setReservationId(billet.getReservation().getId());
        setEtat(billet.getEtat());
    }
}
