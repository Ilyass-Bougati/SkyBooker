package skybooker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "aeroports")
public class Aeroport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String iataCode;
    private String icaoCode;
    private String nom;
    private double latitude;
    private double longitude;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ville_id", nullable = false)
    private Ville ville;

    @OneToMany(mappedBy = "aeroportArrive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vol> volsArrive = new HashSet<>();

    @OneToMany(mappedBy = "aeroportDepart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vol> volsDepart = new HashSet<>();
}
