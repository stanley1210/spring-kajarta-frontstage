package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.EmployeeVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    // 計算數量
    long countEmployees();

    // 查詢全部
    List<EmployeeVO> findAll();

    // 查詢全部TeamLeader
    List<EmployeeVO> findAllTeamLeader();

    // 單筆查詢，依據員工id查詢單一員工資訊
    Employee findById(Integer employeeId);

    // 多條件分頁查詢，依據員工性別、帳號分類、帳號、姓名、手機、電子信箱、分店、直屬主管、入職日、離職日
    Page<EmployeeVO> findByConditionsWithPagination(EmployeeVO employeeVO);

    // 新增
    EmployeeVO create(EmployeeVO employeeVO);

    // 修改
    EmployeeVO modify(EmployeeVO employeeVO);


}
