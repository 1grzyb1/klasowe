package treasurer.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import treasurer.entity.Contribution;


import java.util.List;

public interface ContributionRepository extends CrudRepository<Contribution, Integer> {
    @Query("SELECT DISTINCT c.contribution FROM Contribution c")
    List<String> findDistinctContributionByContribution();

    @Modifying
    @Query("UPDATE Contribution c SET c.checked = :checked WHERE c.name = :name AND c.contribution = :contribution")
    void updateContribution(@Param("name") String name, @Param("checked") int checked, @Param("contribution") String contribution);

    Iterable<Contribution> findByContribution(@Param("contribution") String contribution);
}
