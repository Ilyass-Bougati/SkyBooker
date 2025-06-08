package skybooker.server.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Reservation;
import skybooker.server.entity.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    @NotNull
    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String telephone;

    @NotNull
    private String adresse;

    private Set<Reservation> reservations = new HashSet<>();

    private Role role;

    public ClientDTO(Client client) {
        setId(client.getId());
        setEmail(client.getEmail());
        setTelephone(client.getTelephone());
        setAdresse(client.getAdresse());
        setReservations(client.getReservations());
        setRole(client.getRole());
    }
}
