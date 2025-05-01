package skybooker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import skybooker.enums.EtatVol;

import java.sql.Time;
import java.util.Date;


@Entity
@Table(name = "vols")
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date dateDepart;
    private Time heureDepart;
    private Date dateArrive;
    private Time hereArrive;

    private EtatVol etat;

    @Min(0)
    private double prix;
}
