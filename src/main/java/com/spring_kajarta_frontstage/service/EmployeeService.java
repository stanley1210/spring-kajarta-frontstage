package com.spring_kajarta_frontstage.service;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.CustomerVO;
import com.kajarta.demo.vo.EmployeeVO;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    // 單筆查詢，依據員工id查詢單一員工資訊
    Employee findById(Integer employeeId);

    // 多條件查詢，依據員工性別、帳號分類、帳號、姓名、手機、電子信箱、分店、直屬主管、入職日、離職日
//    List<EmployeeVO> multiConditionQuery(
//            Character sex,
//            Integer accountType,
//            String account,
//            String name,
//            String phone,
//            String email,
//            Integer branchId,
//            Integer teamLeaderId,
//            LocalDate startDateFrom,
//            LocalDate startDateTo,
//            LocalDate endDateFrom,
//            LocalDate endDateTo
//    );

    // 新增
    EmployeeVO create(EmployeeVO employeeVO);

    // 修改
    EmployeeVO modify(EmployeeVO employeeVO);


}
