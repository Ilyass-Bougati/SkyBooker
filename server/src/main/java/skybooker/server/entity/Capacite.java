package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import skybooker.server.DTO.CapaciteDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "capacites", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"avion_id", "classe_id"})
})
public class Capacite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int capacite;
    private int borneInf;
    private int borneSup;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "avion_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Avion avion;

    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Classe classe;

    public Capacite(CapaciteDTO capaciteDTO) {
        setCapacite(capaciteDTO.getCapacite());
        setBorneInf(capaciteDTO.getBorneInf());
        setBorneSup(capaciteDTO.getBorneSup());
    }
}
