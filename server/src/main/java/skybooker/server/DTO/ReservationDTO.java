package skybooker.server.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Billet;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;
import skybooker.server.enums.EtatReservation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private EtatReservation etat;

    @Min(0)
    private double prixTotal;

    private Long clientId;

    @NotNull
    private Long volId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PassagerData {
        Long passagerId;
        Long classId;
    }

    private Set<PassagerData> passagers = new HashSet<>();

    private LocalDateTime reservedAt;

    public ReservationDTO(Reservation reservation) {
        setId(reservation.getId());
        setEtat(reservation.getEtat());
        setPrixTotal(reservation.getPrixTotal());
        setReservedAt(reservation.getReservedAt());
        setClientId(reservation.getClient().getId());
        setVolId(reservation.getVol().getId());

        setPassagers(new HashSet<>());
        for (Billet billet : reservation.getBillets()) {
            getPassagers().add(new PassagerData(
                    billet.getPassager().getId(),
                    billet.getClasse().getId()
            ));
        }
    }
}
