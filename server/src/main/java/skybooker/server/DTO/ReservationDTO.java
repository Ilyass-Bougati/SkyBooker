package skybooker.server.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.entity.Reservation;
import skybooker.server.enums.EtatReservation;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private long id;

    private EtatReservation etat;

    @Min(0)
    private double prixTotal;

    @NotNull
    private Long clientId;

    @NotNull
    private Long volId;

    private LocalDateTime reservedAt;

    public ReservationDTO(Reservation reservation) {
        setId(reservation.getId());
        setEtat(reservation.getEtat());
        setPrixTotal(reservation.getPrixTotal());
        setReservedAt(reservation.getReservedAt());
        setClientId(reservation.getClient().getId());
        setVolId(reservation.getVol().getId());
    }
}
