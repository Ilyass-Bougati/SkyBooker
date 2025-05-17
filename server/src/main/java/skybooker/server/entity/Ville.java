package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import skybooker.server.DTO.VilleDTO;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "villes")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String pays;

    @JsonIgnore
    @OneToMany(mappedBy = "ville", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Aeroport> aeroports = new HashSet<>();

    public Ville(VilleDTO villeDTO) {
        setNom(villeDTO.getNom());
        setPays(villeDTO.getPays());
    }
}
