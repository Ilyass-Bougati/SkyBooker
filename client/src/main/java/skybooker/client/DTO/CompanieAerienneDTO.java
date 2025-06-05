package DTO;

import java.util.HashMap;
import java.util.Map;

public class CompanieAerienneDTO implements Cacheable<CompanieAerienneDTO>{
    private long id;
    private String nom;
    private String iataCode;
    private String icaoCode;

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

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    // for caching
    private final static HashMap<Long, CompanieAerienneDTO> cache = new HashMap<>();
    private final static String route = "/companie-aerienne/";

    @Override
    public Map<Long, CompanieAerienneDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
