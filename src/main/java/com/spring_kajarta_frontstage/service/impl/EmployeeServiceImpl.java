package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.CustomerVO;
import com.kajarta.demo.vo.EmployeeVO;
import com.spring_kajarta_frontstage.repository.EmployeeRepository;
import com.spring_kajarta_frontstage.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;
    /**
     * 查詢單筆，依據員工id查詢單一員工資訊
     * @param employeeId
     * @return CustomerVO
     */
    @Override
    public Employee findById(Integer employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        return employee.orElse(null);
    }

    // 新增
    @Override
    public EmployeeVO create(EmployeeVO employeeVO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVO, employee);
        employee = employeeRepo.save(employee);
        EmployeeVO employeeVONew = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVONew);
        return employeeVONew;
    }


}
