package skybooker.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "passagers")
public class Passager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @Column(unique = true)
    @NotNull
    private String CIN;

    @NotNull
    @Min(0)
    private int age;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Billet> billets = new HashSet<>();

}
