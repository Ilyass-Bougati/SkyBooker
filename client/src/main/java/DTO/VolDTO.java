package DTO;

import enums.EtatVol;

import java.sql.Time;
import java.util.Date;

public class VolDTO {
    private long id;
    private Date dateDepart;
    private Time heureDepart;
    private Date dateArrive;
    private Time heureArrive;
    private EtatVol etat;
    private double prix;
    private long aeroportDepartId;
    private long aeroportArriveId;
    private long avionId;
}
