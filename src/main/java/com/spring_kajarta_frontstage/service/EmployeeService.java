package com.spring_kajarta_frontstage.service;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.CustomerVO;
import com.kajarta.demo.vo.EmployeeVO;

public interface EmployeeService {
    // 單筆查詢，依據員工id查詢單一員工資訊
    Employee findById(Integer employeeId);

    // 新增
    EmployeeVO create(EmployeeVO employeeVO);

    // 刪除


}
