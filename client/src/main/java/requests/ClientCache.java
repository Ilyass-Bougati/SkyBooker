package requests;

import DTO.Cacheable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ClientCache {

    public static <T> T fetch(String route, Class<T> objectClass) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String res = Client.get(route);
        return mapper.readValue(res, objectClass);
    }

    public static <T extends Cacheable<T>> void add(T dto) {
        dto.getCache().putIfAbsent(dto.getId(), dto);
    }

    public static <T extends Cacheable<T>> T get(Long id, Class<T> objectClass) throws Exception {
        T instance = objectClass.getDeclaredConstructor().newInstance();
        Map<Long, T> cache = instance.getCache();
        String route = instance.getRoute();
        if (cache.containsKey(id)) {
            return cache.get(id);
        } else {
            // Fetching the cities
            T response = fetch(route + id, objectClass);
            add(response);
            return response;
        }
    }

}
