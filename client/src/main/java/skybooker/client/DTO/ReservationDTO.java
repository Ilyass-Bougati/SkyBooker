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

    public long getId() {
        return id;
    }

    public EtatReservation getEtat() {
        return etat;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getVolId() {
        return volId;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}
