package DTO;

import java.util.HashMap;
import java.util.Map;

public class AvionDTO implements Cacheable<AvionDTO> {
    private long id;
    private String iataCode;
    private String icaoCode;
    private String model;
    private double maxDistance;
    private long companieAerienneId;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public long getCompanieAerienneId() {
        return companieAerienneId;
    }

    public void setCompanieAerienneId(long companieAerienneId) {
        this.companieAerienneId = companieAerienneId;
    }

    // for caching
    private final static HashMap<Long, AvionDTO> cache = new HashMap<>();
    private final static String route = "/avion/";

    @Override
    public Map<Long, AvionDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
