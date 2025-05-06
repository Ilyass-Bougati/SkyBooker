package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "companie_aerienne")
public class CompanieAerienne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String iacaCode;
    private String icaoCode;

    @JsonIgnore
    @OneToMany(mappedBy = "companieAerienne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Avion> avions = new HashSet<>();
}
