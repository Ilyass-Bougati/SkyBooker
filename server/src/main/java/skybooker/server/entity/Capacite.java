package skybooker.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "capacites")
public class Capacite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int capacite;
    private int borneInf;
    private int borneSup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "avion_id", nullable = false)
    private Avion avion;

    @OneToOne(cascade = CascadeType.ALL)
    private Classe classe;
}
