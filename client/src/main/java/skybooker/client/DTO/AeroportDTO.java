package skybooker.client.DTO;

import java.util.HashMap;
import java.util.Map;

public class AeroportDTO implements Cacheable<AeroportDTO> {
    private long id;
    private String iataCode;
    private String icaoCode;
    private String nom;
    private double latitude;
    private double longitude;
    private long villeId;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getVilleId() {
        return villeId;
    }

    public void setVilleId(long villeId) {
        this.villeId = villeId;
    }

    // for caching
    private final static HashMap<Long, AeroportDTO> cache = new HashMap<>();
    private final static String route = "/aeroport/";

    @Override
    public Map<Long, AeroportDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
