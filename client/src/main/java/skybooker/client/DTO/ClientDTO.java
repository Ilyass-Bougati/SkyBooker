package skybooker.client.DTO;

import java.util.HashSet;
import java.util.Set;
public class ClientDTO {
    private long id;
    private String email;
    private String telephone;
    private String adresse;
    private Set<ReservationDTO> reservations = new HashSet<>();
    private PassagerDTO passager;
    private RoleDTO role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationDTO> reservations) {
        this.reservations = reservations;
    }

    public PassagerDTO getPassager() {
        return passager;
    }

    public void setPassager(PassagerDTO passager) {
        this.passager = passager;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
