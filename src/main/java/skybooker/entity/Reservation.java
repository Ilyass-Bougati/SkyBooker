package skybooker.entity;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import skybooker.enums.EtatReservation;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private EtatReservation etat = EtatReservation.PENDING;
    @Min(0)
    private double price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "reserved_at")
    @CreationTimestamp
    private LocalDateTime reservedAt;
}
