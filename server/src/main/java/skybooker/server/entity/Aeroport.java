package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.DTO.AeroportDTO;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "ville_id", nullable = false)
    private Ville ville;

    @JsonIgnore
    @OneToMany(mappedBy = "aeroportArrive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vol> volsArrive = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "aeroportDepart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vol> volsDepart = new HashSet<>();

    public Aeroport(AeroportDTO aeroportDTO) {
        setIataCode(aeroportDTO.getIataCode());
        setIcaoCode(aeroportDTO.getIcaoCode());
        setNom(aeroportDTO.getNom());
        setLatitude(aeroportDTO.getLatitude());
        setLongitude(aeroportDTO.getLongitude());
        setVolsArrive(new HashSet<>());
        setVolsDepart(new HashSet<>());
    }
}
