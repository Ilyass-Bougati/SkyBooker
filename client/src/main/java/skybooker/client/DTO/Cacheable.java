package skybooker.client.DTO;

import java.util.Map;

public interface Cacheable<T> {
    public Map<Long, T> getCache();
    public String getRoute();
    public Long getId();
}
