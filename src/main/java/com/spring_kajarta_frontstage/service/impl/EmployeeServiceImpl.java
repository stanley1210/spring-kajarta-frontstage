package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.enums.BranchEnum;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.EmployeeVO;
import com.spring_kajarta_frontstage.repository.EmployeeRepository;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public long countEmployees() {
        return employeeRepo.count();
    }

    // 查詢全部
    @Override
    public List<EmployeeVO> findAll() {
        List<Employee> employees = employeeRepo.findAll();
        log.info("查詢到 {} 條員工數據", employees.size());
        List<EmployeeVO> employeeVOList = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVO);
            employeeVO.setCreateTime(
                    employee.getCreateTime() != null ?
                            DatetimeConverter.toString(new Date(employee.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) :
                            null
            );
            employeeVO.setUpdateTime(
                    employee.getUpdateTime() != null ?
                            DatetimeConverter.toString(new Date(employee.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) :
                            null
            );
            employeeVOList.add(employeeVO);
        }
        log.info("轉換後的 EmployeeVO 數量為 {}", employeeVOList.size());
        return employeeVOList;
    }

    /**
     * 查詢單筆，依據員工id查詢單一員工資訊
     *
     * @param employeeId
     * @return CustomerVO
     */
    @Override
    public Employee findById(Integer employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        return employee.orElse(null);
    }

    // 多條件查詢，依據員工性別、帳號分類、帳號、姓名、手機、電子信箱、分店、直屬主管、入職日、離職日
    @Override
    public Page<EmployeeVO> findByConditionsWithPagination(Character sex, Integer accountType, String account, String name, String phone, String email, Integer branch, Integer teamLeaderId, LocalDate startDate, LocalDate endDate, int page, int size, String sort, boolean dir) {
        Sort sortOrder = dir ? Sort.by(Sort.Direction.ASC, sort) : Sort.by(Sort.Direction.DESC, sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);


        Page<Employee> employeePage = employeeRepo.findByMultipleConditions(
                sex, accountType, account, name, phone, email, branch, teamLeaderId, startDate, endDate, pageable);

        return employeePage.map(employee -> {
            EmployeeVO employeeVO = new EmployeeVO();
            employeeVO.setBranchCity(BranchEnum.getByCode(employee.getBranch()).getCity());
            employeeVO.setBranchAddress(BranchEnum.getByCode(employee.getBranch()).getAddress());
            employeeVO.setBranchName(BranchEnum.getByCode(employee.getBranch()).getBranchName());
            BeanUtils.copyProperties(employee, employeeVO);
            employeeVO.setCreateTime(DatetimeConverter.toString(new Date(employee.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            employeeVO.setUpdateTime(DatetimeConverter.toString(new Date(employee.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));

            return employeeVO;
        });
    }


    // 新增
    @Override
    public EmployeeVO create(EmployeeVO employeeVO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVO, employee);
        employeeRepo.save(employee);
        EmployeeVO employeeVONew = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVONew);
        employeeVO.setCreateTime(DatetimeConverter.toString(new Date(employee.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
        employeeVO.setUpdateTime(DatetimeConverter.toString(new Date(employee.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
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
