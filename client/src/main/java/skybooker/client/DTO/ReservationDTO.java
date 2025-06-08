package skybooker.client.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import skybooker.client.enums.EtatReservation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReservationDTO implements Cacheable<ReservationDTO> {

    private long id;

    private EtatReservation etat;

    private double prixTotal;

    private Long clientId;

    private Long volId;

    public static class PassagerData {
        Long passagerId;
        Long classId;

        public Long getPassagerId() {
            return passagerId;
        }

        public void setPassagerId(Long passagerId) {
            this.passagerId = passagerId;
        }

        public Long getClassId() {
            return classId;
        }

        public void setClassId(Long classId) {
            this.classId = classId;
        }
    }

    private Set<PassagerData> passagers = new HashSet<>();

    private LocalDateTime reservedAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EtatReservation getEtat() {
        return etat;
    }

    public void setEtat(EtatReservation etat) {
        this.etat = etat;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getVolId() {
        return volId;
    }

    public void setVolId(Long volId) {
        this.volId = volId;
    }

    public Set<PassagerData> getPassagers() {
        return passagers;
    }

    public void setPassagers(Set<PassagerData> passagers) {
        this.passagers = passagers;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(LocalDateTime reservedAt) {
        this.reservedAt = reservedAt;
    }

    // for caching
    @JsonIgnore
    private final static HashMap<Long, ReservationDTO> cache = new HashMap<>();

    @JsonIgnore
    private final static String route = "/aeroport/";

    @Override
    @JsonIgnore
    public Map<Long, ReservationDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
