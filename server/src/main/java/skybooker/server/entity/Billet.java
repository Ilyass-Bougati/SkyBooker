package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import skybooker.server.DTO.BilletDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "billets")
public class Billet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int siege;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Classe classe;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "passager_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Passager passager;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Reservation reservation;
}
