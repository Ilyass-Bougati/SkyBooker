package skybooker.client.DTO;

import java.util.HashMap;
import java.util.Map;

public class ClassDTO implements Cacheable<ClassDTO>{
    private long id;
    private String nom;
    private int prixParKm;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrixParKm() {
        return prixParKm;
    }

    public void setPrixParKm(int prixParKm) {
        this.prixParKm = prixParKm;
    }

    // for caching
    private final static HashMap<Long, ClassDTO> cache = new HashMap<>();
    private final static String route = "/aeroport/";

    @Override
    public Map<Long, ClassDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
