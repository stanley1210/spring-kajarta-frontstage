package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Integer> {
    // 多條件查詢，依據假單的請假或給假狀態、開始時段、結束時段、假種、休假員工、核可主管、核可狀態、使用期限(開始)、使用期限(結束)
    @Query("SELECT l FROM Leave l WHERE "
            + "(:leaveStatus IS NULL OR l.leaveStatus = :leaveStatus) AND "
            + "(:startTime IS NULL OR l.startTime = :startTime) AND "
            + "(:endTime IS NULL OR l.endTime = :endTime) AND "
            + "(:leaveType IS NULL OR l.leaveType = :leaveType) AND "
            + "(:employee IS NULL OR l.employee.id = :employee) AND "
            + "(:teamLeaderId IS NULL OR l.teamLeaderId = :teamLeaderId) AND "
            + "(:permisionStatus IS NULL OR l.permisionStatus = :permisionStatus) AND "
            + "(:validityPeriodStart IS NULL OR l.validityPeriodStart = :validityPeriodStart) AND "
            + "(:validityPeriodEnd IS NULL OR l.validityPeriodEnd = :validityPeriodEnd)")
    List<Leave> findByMultipleConditions(
            @Param("leaveStatus") Integer leaveStatus,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("leaveType") Integer leaveType,
            @Param("employee") Integer employee,
            @Param("teamLeaderId") Integer teamLeaderId,
            @Param("permisionStatus") Integer permisionStatus,
            @Param("validityPeriodStart") Date validityPeriodStart,
            @Param("validityPeriodEnd") Date validityPeriodEnd);

}
