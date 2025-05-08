package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import skybooker.server.DTO.ClientDTO;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "clients")
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    /**
     * This function is meant to update all the fields of a client
     * @param client This is the client's new fields
     */
    public void updateFields(Client client) {
        setEmail(client.getEmail());
        setPassword(BCrypt.hashpw(client.getPassword(), BCrypt.gensalt()));
        setTelephone(client.getTelephone());
        setAdresse(client.getAdresse());
        setPassager(client.getPassager());
    }

    /**
     * This function changes only the email, telephone and adresse of a client
     * to change the reservations, the passager or the password you should pass
     * through other services/methods
     *
     * @param clientDTO The new details of the client
     */
    public void updateFields(ClientDTO clientDTO) {
        setEmail(clientDTO.getEmail());
        setTelephone(clientDTO.getTelephone());
        setAdresse(clientDTO.getAdresse());
    }

    /**
     * This function checks if a client is admin
     *
     * @return boolean that indicates if the client is admin
     */
    public boolean isAdmin() {
        return getRole().getId() == 2;
    }
}
