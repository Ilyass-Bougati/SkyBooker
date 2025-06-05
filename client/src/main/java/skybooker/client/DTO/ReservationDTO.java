package skybooker.client.DTO;

import skybooker.client.enums.EtatReservation;

import java.time.LocalDateTime;

public class ReservationDTO {
    private long id;
    private EtatReservation etat;
    private double prixTotal;
    private Long clientId;
    private Long volId;
    private LocalDateTime reservedAt;
}
