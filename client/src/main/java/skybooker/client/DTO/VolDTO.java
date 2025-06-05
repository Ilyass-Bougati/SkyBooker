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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Time getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(Time heureDepart) {
        this.heureDepart = heureDepart;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Time getHeureArrive() {
        return heureArrive;
    }

    public void setHeureArrive(Time heureArrive) {
        this.heureArrive = heureArrive;
    }

    public EtatVol getEtat() {
        return etat;
    }

    public void setEtat(EtatVol etat) {
        this.etat = etat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public long getAeroportDepartId() {
        return aeroportDepartId;
    }

    public void setAeroportDepartId(long aeroportDepartId) {
        this.aeroportDepartId = aeroportDepartId;
    }

    public long getAeroportArriveId() {
        return aeroportArriveId;
    }

    public void setAeroportArriveId(long aeroportArriveId) {
        this.aeroportArriveId = aeroportArriveId;
    }

    public long getAvionId() {
        return avionId;
    }

    public void setAvionId(long avionId) {
        this.avionId = avionId;
    }
}
