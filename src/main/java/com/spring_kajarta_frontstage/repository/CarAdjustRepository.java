package com.spring_kajarta_frontstage.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.CarAdjust;
import com.kajarta.demo.model.Employee;

@Repository
public interface CarAdjustRepository extends JpaRepository<CarAdjust, Integer> {

        @Query("SELECT caj FROM CarAdjust caj "
                        + "INNER JOIN caj.car car "
                        + "INNER JOIN car.carinfo carinfo  "
                        + "WHERE (:id IS NULL OR caj.id = :id) AND "
                        + "(:teamLeaderId IS NULL OR caj.teamLeaderId = :teamLeaderId) AND "
                        + "(:employee IS NULL OR caj.employee = :employee) AND "
                        + "(:car IS NULL OR caj.car = :car) AND "
                        + "(:approvalStatus IS NULL OR caj.approvalStatus = :approvalStatus) AND "
                        + "(:approvalType IS NULL OR caj.approvalType = :approvalType) AND "
                        + "(:floatingAmountMax IS NULL OR :floatingAmountMin IS NULL OR (caj.floatingAmount <= :floatingAmountMax AND caj.floatingAmount >= :floatingAmountMin)) AND "

                        + "(:createTimeStr IS NULL OR :createTimeEnd IS NULL OR caj.createTime BETWEEN :createTimeStr AND :createTimeEnd ) AND "
                        + "(:updateTimeStr IS NULL OR :updateTimeEnd IS NULL OR caj.updateTime BETWEEN :updateTimeStr AND :updateTimeEnd ) AND "
                        + "(:brandId IS NULL OR carinfo.brand = :brandId)")
        public Page<CarAdjust> findByHQL(@Param("id") Integer id,
                        @Param("teamLeaderId") Integer teamLeaderId,
                        @Param("employee") Employee employee,
                        @Param("car") Car car,
                        @Param("approvalStatus") Integer approvalStatus,
                        @Param("approvalType") Integer approvalType,
                        @Param("floatingAmountMax") BigDecimal floatingAmountMax,
                        @Param("floatingAmountMin") BigDecimal floatingAmountMin,
                        @Param("createTimeStr") Date createTimeStr,
                        @Param("createTimeEnd") Date createTimeEnd,
                        @Param("updateTimeStr") Date updateTimeStr,
                        @Param("updateTimeEnd") Date updateTimeEnd,
                        @Param("brandId") Integer brandId,
                        Pageable pageable);
}
