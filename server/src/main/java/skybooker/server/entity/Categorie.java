package skybooker.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;

    @Max(1)
    private double reduction;
}
