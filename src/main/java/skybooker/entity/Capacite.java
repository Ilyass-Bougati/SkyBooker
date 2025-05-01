package skybooker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "capacites")
public class Capacite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int capacite;
}
