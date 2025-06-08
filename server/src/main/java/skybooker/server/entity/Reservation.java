package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.enums.EtatReservation;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EtatReservation etat = EtatReservation.PENDING;

    @Min(0)
    private double prixTotal;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "reserved_at")
    @CreationTimestamp
    private LocalDateTime reservedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Billet> billets = new HashSet<>();

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_id", nullable = false)
    private Vol vol;

    public Reservation(ReservationDTO reservationDTO) {
        setEtat(reservationDTO.getEtat());
        setReservedAt(reservationDTO.getReservedAt());
        setPrixTotal(reservationDTO.getPrixTotal());
        billets = new HashSet<>();
    }
}
