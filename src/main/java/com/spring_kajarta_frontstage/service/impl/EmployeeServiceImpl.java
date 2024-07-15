package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.CustomerVO;
import com.kajarta.demo.vo.EmployeeVO;
import com.spring_kajarta_frontstage.repository.EmployeeRepository;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.spring_kajarta_frontstage.util.DatetimeConverter.YYYY_MM_DD_HH_MM_SS;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * 查詢單筆，依據員工id查詢單一員工資訊
     *
     * @param employeeId
     * @return CustomerVO
     */
    @Override
    public Employee findById(Integer employeeId) {
        Optional<Employee> employeeOptional = employeeRepo.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            // 将创建时间和更新时间转换为字符串格式
            employee.setCreateTimeString(DatetimeConverter.toString(employee.getCreateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            employee.setUpdateTimeString(DatetimeConverter.toString(employee.getUpdateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            return employee;
        } else {
            return null;
        }
    }

    // 多條件查詢，依據員工性別、帳號分類、帳號、姓名、手機、電子信箱、分店、直屬主管、入職日、離職日
    @Override
    public List<EmployeeVO> multiConditionQuery(Character sex, Integer accountType, String account, String name, String phone, String email, Integer branch, Integer teamLeaderId, LocalDate startDate, LocalDate endDate) {
        List<Employee> employees = employeeRepo.findByMultipleConditions(
                sex, accountType, account, name, phone, email, branch, teamLeaderId, startDate, endDate);

        List<EmployeeVO> employeeVOList = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVO);
            employeeVO.setCreateTime(DatetimeConverter.toString(new Date(employee.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            employeeVO.setUpdateTime(DatetimeConverter.toString(new Date(employee.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            employeeVOList.add(employeeVO);
        }
        return employeeVOList;
    }


    // 新增
    @Override
    public EmployeeVO create(EmployeeVO employeeVO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVO, employee);
        employeeRepo.save(employee);
        EmployeeVO employeeVONew = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVONew);
        return employeeVONew;
    }

    // 修改
    @Transactional
    @Override
    public EmployeeVO modify(EmployeeVO employeeVO) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(employeeVO.getId());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            BeanUtils.copyProperties(employeeVO, employee,"createTime", "updateTime");
            employeeRepo.save(employee);
            EmployeeVO updateEmployeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, updateEmployeeVO);
            return updateEmployeeVO;
        } else {
            return null;
        }
    }
}
