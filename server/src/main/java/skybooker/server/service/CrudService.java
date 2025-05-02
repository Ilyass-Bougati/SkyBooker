package skybooker.server.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T create(T entity);
    T update(T entity);
    void deleteById(ID id);
    void delete(T entity);
}
