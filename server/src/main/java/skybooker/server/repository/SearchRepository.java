package skybooker.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skybooker.server.entity.Search;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query("select s from Search s where s.client.id=:clientId " +
            "order by s.searchedAt desc limit 3")
    List<Search> getHistory(Long clientId);
}
