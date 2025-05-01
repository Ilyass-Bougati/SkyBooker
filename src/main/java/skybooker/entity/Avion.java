package skybooker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vol> vols = new HashSet<>();

    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Capacite> capacites = new HashSet<>();


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "companie_aerienne_id", nullable = false)
    private CompanieAerienne companieAerienne;

}
