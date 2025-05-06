package skybooker.server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private Set<Reservation> reservations = new HashSet<>();

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
