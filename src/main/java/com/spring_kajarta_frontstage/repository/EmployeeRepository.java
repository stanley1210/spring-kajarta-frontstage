package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // 多條件查詢，依據用戶性別、帳號、城市、姓名
}
