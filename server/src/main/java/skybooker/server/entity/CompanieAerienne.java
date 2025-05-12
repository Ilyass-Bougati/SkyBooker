package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import skybooker.server.DTO.CompanieAerienneDTO;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "companie_aerienne")
public class CompanieAerienne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String iataCode;
    private String icaoCode;

    @JsonIgnore
    @OneToMany(mappedBy = "companieAerienne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Avion> avions = new HashSet<>();

    public CompanieAerienne(CompanieAerienneDTO companieAerienneDTO) {
        setNom(companieAerienneDTO.getNom());
        setIataCode(companieAerienneDTO.getIataCode());
        setIcaoCode(companieAerienneDTO.getIcaoCode());
        setAvions(new HashSet<>());
    }
}
