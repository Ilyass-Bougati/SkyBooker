package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import skybooker.server.DTO.VolDTO;
import skybooker.server.enums.EtatVol;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vols")
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date dateDepart;
    private Time heureDepart;
    private Date dateArrive;
    private Time heureArrive;

    @Enumerated(EnumType.ORDINAL) // Map enum to integer ordinal in DB
    private EtatVol etat;

    @Min(0)
    private double prix;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroport_depart_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Aeroport aeroportDepart;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroport_arrive_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Aeroport aeroportArrive;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "avion_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Avion avion;

    @JsonIgnore
    @OneToMany(mappedBy = "vol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Reservation> reservations = new HashSet<>();

    public Vol(VolDTO volDTO) {
        setId(volDTO.getId());
        setDateDepart(volDTO.getDateDepart());
        setHeureDepart(volDTO.getHeureDepart());
        setDateArrive(volDTO.getDateArrive());
        setHeureArrive(volDTO.getHeureArrive());
        setEtat(volDTO.getEtat());
        setPrix(volDTO.getPrix());
        setReservations(new HashSet<>());
    }
}
