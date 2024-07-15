package com.spring_kajarta_frontstage.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Integer> {
        @Query("SELECT kpi FROM Kpi kpi WHERE (:id IS NULL OR kpi.id = :id) AND "
                        + "(:selectStrDay IS NULL OR :selectEndDay IS NULL OR kpi.seasonStrDay BETWEEN :selectStrDay AND :selectEndDay ) AND "
                        + "(:employee IS NULL OR kpi.employee = :employee) AND "
                        + "(:teamLeader IS NULL OR kpi.employee.teamLeader = :teamLeader) AND "
                        + "(:teamLeaderRatingMax IS NULL OR :teamLeaderRatingMin IS NULL OR (kpi.teamLeaderRating <= :teamLeaderRatingMax AND kpi.teamLeaderRating >= :teamLeaderRatingMin )) AND "
                        + "(:salesScoreMax IS NULL OR :salesScoreMin IS NULL OR (kpi.salesScore <= :salesScoreMax AND kpi.salesScore >= :salesScoreMin )) AND "
                        + "(:totalScoreMax IS NULL OR :totalScoreMin IS NULL OR (kpi.totalScore <= :totalScoreMax AND kpi.totalScore >= :totalScoreMin)) ")
        public Page<Kpi> findByHQL(@Param("id") Integer id,
                        @Param("selectStrDay") Date selectStrDay,
                        @Param("selectEndDay") Date selectEndDay,
                        @Param("employee") Employee employee,
                        @Param("teamLeader") Employee teamLeader,
                        @Param("teamLeaderRatingMax") Integer teamLeaderRatingMax,
                        @Param("teamLeaderRatingMin") Integer teamLeaderRatingMin,
                        @Param("salesScoreMax") Integer salesScoreMax,
                        @Param("salesScoreMin") Integer salesScoreMin,
                        @Param("totalScoreMax") BigDecimal totalScoreMax,
                        @Param("totalScoreMin") BigDecimal totalScoreMin,
                        Pageable pageable);
}
