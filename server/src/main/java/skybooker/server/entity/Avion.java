package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import skybooker.server.DTO.AvionDTO;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "avions")
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String iataCode;
    private String icaoCode;
    private String model;
    private double maxDistance;

    @JsonIgnore
    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Vol> vols = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Capacite> capacites = new HashSet<>();


    @ManyToOne(optional = false)
    @JoinColumn(name = "companie_aerienne_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CompanieAerienne companieAerienne;

    /**
     * This function create a Avion entity from a AvionDTO object
     * @param avionDTO the Avion transfer object
     */
    public Avion(AvionDTO avionDTO) {
        setIataCode(avionDTO.getIataCode());
        setIcaoCode(avionDTO.getIcaoCode());
        setModel(avionDTO.getModel());
        setMaxDistance(avionDTO.getMaxDistance());
        setVols(new HashSet<>());
        setCapacites(new HashSet<>());
    }
}
