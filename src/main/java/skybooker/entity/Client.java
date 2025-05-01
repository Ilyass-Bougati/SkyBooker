package skybooker.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String telephone;
    @NotNull
    private String adresse;
    @NotNull
    private String CIN;

//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Reservation> reservations = new HashSet<>();
}
