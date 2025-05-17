package skybooker.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;

    @Max(1)
    private double reduction;

    public void updateFields(Categorie categorie) {
        setReduction(categorie.getReduction());
        setNom(categorie.getNom());
    }
}
