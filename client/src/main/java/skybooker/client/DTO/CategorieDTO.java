package skybooker.client.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class CategorieDTO implements Cacheable<CategorieDTO> {
    private long id;
    private String nom;
    private double reduction;

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

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    // for caching
    @JsonIgnore
    private final static HashMap<Long, CategorieDTO> cache = new HashMap<>();

    @JsonIgnore
    private final static String route = "/categories/";

    @Override
    @JsonIgnore
    public Map<Long, CategorieDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
