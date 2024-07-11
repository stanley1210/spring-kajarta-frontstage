package com.spring_kajarta_frontstage.service;
import com.kajarta.demo.model.Employee;

public interface EmployeeService {
    // 單筆查詢，依據員工id查詢單一員工資訊
    Employee findById(Integer employeeId);

}
