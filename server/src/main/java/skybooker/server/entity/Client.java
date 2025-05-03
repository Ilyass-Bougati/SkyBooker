package skybooker.server.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "clients")
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String telephone;
    @NotNull
    private String adresse;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Passager passager;

    /**
     * This function is meant to update all the fields of a client
     * @param client This is the client's new fields
     */
    public void updateFields(Client client) {
        setEmail(client.getEmail());
        // TODO : fix this the password needs to be hashed
        setPassword(client.getPassword());
        setTelephone(client.getTelephone());
        setAdresse(client.getAdresse());
        setPassager(client.getPassager());
    }
}
