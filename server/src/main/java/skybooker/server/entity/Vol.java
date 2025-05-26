package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDepart;

    private Time heureDepart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateArrive;

    private Time heureArrive;

    @Enumerated(EnumType.STRING) // Map enum to String in DB
    private EtatVol etat;

    @Min(0)
    private double prix;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroport_depart_id", nullable = false)
    private Aeroport aeroportDepart;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aeroport_arrive_id", nullable = false)
    private Aeroport aeroportArrive;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "avion_id", nullable = false)
    private Avion avion;

    @JsonIgnore
    @OneToMany(mappedBy = "vol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
