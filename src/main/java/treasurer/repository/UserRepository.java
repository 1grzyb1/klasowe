package treasurer.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import treasurer.entity.People;


import java.util.List;

public interface UserRepository extends CrudRepository<People, Integer> {
    public List<People> findByName(String name);

    @Modifying
    @Query("UPDATE People p SET p.money = :money WHERE p.name = :name")
    void updateMoney(@Param("name") String name, @Param("money") int money);

    People findFirstByName(@Param("name") String name);
}
