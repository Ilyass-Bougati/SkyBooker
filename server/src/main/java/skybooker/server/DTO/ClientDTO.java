package skybooker.server.DTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;

import java.util.HashSet;
import java.util.Set;

@Data
public class ClientDTO {
    @NotNull
    private long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String telephone;

    @NotNull
    private String adresse;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Passager passager;

    public ClientDTO(Client client) {
        setId(client.getId());
        setEmail(client.getEmail());
        setTelephone(client.getTelephone());
        setAdresse(client.getAdresse());
        setReservations(client.getReservations());
        setPassager(client.getPassager());
    }
}
