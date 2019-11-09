package treasurer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import treasurer.entity.History;

public interface HistoryRepository extends CrudRepository<History, Integer> {
    Iterable<History> findByWhat(@Param("what") String what);
}

