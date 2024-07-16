package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // 多條件查詢，依據員工性別、帳號分類、帳號、姓名、手機、電子信箱、分店、直屬主管、入職日、離職日

    @Query("SELECT e FROM Employee e WHERE "
            + "(:sex IS NULL OR e.sex = :sex) AND "
            + "(:accountType IS NULL OR e.accountType = :accountType) AND "
            + "(:account IS NULL OR e.account = :account) AND "
            + "(:name IS NULL OR e.name LIKE %:name%) AND "
            + "(:phone IS NULL OR e.phone = :phone) AND "
            + "(:email IS NULL OR e.email = :email) AND "
            + "(:branch IS NULL OR e.branch = :branch) AND "
            + "(:teamLeaderId IS NULL OR e.teamLeader.id = :teamLeaderId) AND "
            + "(:startDate IS NULL OR e.startDate = :startDate) AND "
            + "(:endDate IS NULL OR e.endDate = :endDate)")
    List<Employee> findByMultipleConditions(
            @Param("sex") Character sex,
            @Param("accountType") Integer accountType,
            @Param("account") String account,
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("branch") Integer branch,
            @Param("teamLeaderId") Integer teamLeaderId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}



