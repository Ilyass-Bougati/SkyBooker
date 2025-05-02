package skybooker.server.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    List<T> findAll();
    Optional<T> getById(ID id);
    T create(T entity);
    void deleteById(ID id);
    void delete(T entity);
}
