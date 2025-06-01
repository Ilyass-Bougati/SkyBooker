package DTO;

import java.util.HashMap;
import java.util.Map;

public class VilleDTO implements Cacheable<VilleDTO> {
    private long id;
    private String nom;
    private String pays;

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

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }


    // for caching
    private final static HashMap<Long, VilleDTO> cache = new HashMap<>();
    private final static String route = "/ville/";

    @Override
    public Map<Long, VilleDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
