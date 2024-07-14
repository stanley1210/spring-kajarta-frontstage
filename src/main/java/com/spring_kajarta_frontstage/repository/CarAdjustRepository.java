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

    // @Query("SELECT caj FROM CarAdjust caj WHERE (:id IS NULL OR caj.id = :id) AND
    // "
    // + "(:teamLeaderId IS NULL OR caj.teamLeaderId = :teamLeaderId) AND "
    // + "(:employee IS NULL OR caj.employee = :employee) AND "
    // + "(:car IS NULL OR caj.car = :car) AND "
    // + "(:approvalStatus IS NULL OR caj.approvalStatus = :approvalStatus) AND "
    // + "(:approvalType IS NULL OR caj.approvalType = :approvalType) AND "
    // + "(:floatingAmount IS NULL OR caj.floatingAmount > :floatingAmount) AND "
    // + "(:unavailableTimeStr IS NULL OR caj.unavailableTimeStr >
    // :unavailableTimeStr) AND "
    // + "(:createTime IS NULL OR caj.createTime > :createTime) "
    // + "(:updateTime IS NULL OR caj.updateTime > :updateTime) ")
    // public Page<CarAdjust> findByHQL(@Param("id") Integer id,
    // @Param("teamLeaderId") Employee teamLeaderId,
    // @Param("employee") Employee employee,
    // @Param("car") Car car,
    // @Param("approvalStatus") Integer approvalStatus,
    // @Param("approvalType") Integer approvalType,
    // @Param("floatingAmount") BigDecimal floatingAmount,
    // @Param("unavailableTimeStr") Date unavailableTimeStr,
    // @Param("createTime") Date createTime,
    // @Param("updateTime") Date updateTime,
    // Pageable pageable);
}
