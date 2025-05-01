package skybooker.entity;

import jakarta.persistence.*;

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
}
