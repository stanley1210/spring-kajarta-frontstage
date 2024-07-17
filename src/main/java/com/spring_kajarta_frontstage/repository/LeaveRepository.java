package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.dto.LeaveDTO;
import com.kajarta.demo.model.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Integer> {
    // 多條件查詢，依據假單的請假或給假狀態、開始時段、結束時段、假種、休假員工、核可主管、核可狀態、使用期限(開始)、使用期限(結束)

    @Query("SELECT new com.kajarta.demo.dto.LeaveDTO(l.id, l.leaveStatus, l.startTime, l.endTime, l.leaveType, " +
            "l.employee.id, l.deputyId, l.teamLeaderId, l.permisionRemarks, l.permisionStatus, " +
            "l.auditTime, l.reason, l.actualLeaveHours, l.image, l.specialLeaveHours, " +
            "l.createTime, l.updateTime, l.validityPeriodStart, l.validityPeriodEnd, e.name) " +
            "FROM Leave l LEFT JOIN Employee e ON e.id = l.deputyId " +
            "WHERE (:leaveStatus IS NULL OR l.leaveStatus = :leaveStatus) AND " +
            "(:startTime IS NULL OR l.startTime = :startTime) AND " +
            "(:endTime IS NULL OR l.endTime = :endTime) AND " +
            "(:leaveType IS NULL OR l.leaveType = :leaveType) AND " +
            "(:employee IS NULL OR l.employee.id = :employee) AND " +
            "(:teamLeaderId IS NULL OR l.teamLeaderId = :teamLeaderId) AND " +
            "(:permisionStatus IS NULL OR l.permisionStatus = :permisionStatus) AND " +
            "(:validityPeriodStart IS NULL OR l.validityPeriodStart = :validityPeriodStart) AND " +
            "(:validityPeriodEnd IS NULL OR l.validityPeriodEnd = :validityPeriodEnd) " +
            "ORDER BY l.createTime ASC")
    Page<LeaveDTO> findAllByMultipleConditions(
            @Param("leaveStatus") Integer leaveStatus,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("leaveType") Integer leaveType,
            @Param("employee") Integer employee,
            @Param("teamLeaderId") Integer teamLeaderId,
            @Param("permisionStatus") Integer permisionStatus,
            @Param("validityPeriodStart") String validityPeriodStart,
            @Param("validityPeriodEnd") String validityPeriodEnd,
            Pageable pageable);
//    List<Leave> findByMultipleConditions(
//            @Param("leaveStatus") Integer leaveStatus,
//            @Param("startTime") String startTime,
//            @Param("endTime") String endTime,
//            @Param("leaveType") Integer leaveType,
//            @Param("employee") Integer employee,
//            @Param("teamLeaderId") Integer teamLeaderId,
//            @Param("permisionStatus") Integer permisionStatus,
//            @Param("validityPeriodStart") String validityPeriodStart,
//            @Param("validityPeriodEnd") String validityPeriodEnd);

}
