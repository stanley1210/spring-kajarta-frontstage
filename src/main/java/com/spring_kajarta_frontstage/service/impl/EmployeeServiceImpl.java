package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.enums.AccountTypeEnum;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<EmployeeVO> findAllTeamLeader() {
        List<Employee> employees = employeeRepo.findAllTeamLeader();
        List<EmployeeVO> employeeVOList = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVO);
            employeeVO.setTeamLeaderId(employee.getId());
            employeeVO.setTeamLeaderName(employee.getName());
            employeeVOList.add(employeeVO);
        }
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

    /**
     * 多條件查詢，依據員工性別、帳號分類、帳號、姓名、手機、電子信箱、分店、直屬主管、入職日、離職日
     */
    @Override
    public Page<EmployeeVO> findByConditionsWithPagination(EmployeeVO employeeVO) {
        Pageable pageable = PageRequest.of(employeeVO.getPageNum(), employeeVO.getPageSize());
        Integer teamLeaderId = (employeeVO.getTeamLeaderId() != null) ? employeeVO.getTeamLeaderId() : null;

        Page<Employee> employeePage = employeeRepo.findByMultipleConditions(
                employeeVO.getSex(), employeeVO.getAccountType(), employeeVO.getAccount(), employeeVO.getName(), employeeVO.getPhone(), employeeVO.getEmail()
                , employeeVO.getBranch(), teamLeaderId, employeeVO.getStartDate(), employeeVO.getEndDate(), pageable);

        return employeePage.map(employee -> {
            EmployeeVO employeeVONew = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVONew);
            employeeVONew.setBranchCity(BranchEnum.getByCode(employee.getBranch()).getCity());
            employeeVONew.setBranchAddress(BranchEnum.getByCode(employee.getBranch()).getAddress());
            employeeVONew.setBranchName(BranchEnum.getByCode(employee.getBranch()).getBranchName());
            employeeVONew.setAccountTypeName(AccountTypeEnum.getByCode(employee.getAccountType()).getAccountType());
            employeeVONew.setTeamLeaderId(employee.getTeamLeader().getId());
            employeeVONew.setTeamLeaderName(employee.getTeamLeader().getName());
            employeeVONew.setCreateTime(DatetimeConverter.toString(new Date(employee.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            employeeVONew.setUpdateTime(DatetimeConverter.toString(new Date(employee.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));

            return employeeVONew;
        });
    }


    // 新增
    @Override
    public EmployeeVO create(EmployeeVO employeeVO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVO, employee);
        // 查詢並設置employee
        Optional<Employee> employeeOptional = employeeRepo.findById(employeeVO.getTeamLeaderId());
        if (!employeeOptional.isPresent()) {
            throw new RuntimeException("找不到此員工");
        }

        employee.setTeamLeader(employeeOptional.get());
        // 設置 createTime 為當前時間
        Date now = new Date();
        employee.setCreateTime(now);

        // 當 updateTime 為空值時，設置為與 createTime 相同的時間
        if (employee.getUpdateTime() == null) {
            employee.setUpdateTime(now);
        }
        employeeRepo.save(employee);
        EmployeeVO employeeVONew = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVONew);
        employeeVONew.setTeamLeaderId(employee.getTeamLeader().getId());
        employeeVONew.setTeamLeaderName(employee.getTeamLeader().getName());
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
            // 使用 setter 方法更新屬性
            if (employeeVO.getAccountType() != null) {
                employee.setAccountType(employeeVO.getAccountType());
            }
            if (employeeVO.getAccount() != null) {
                employee.setAccount(employeeVO.getAccount());
            }
            if (employeeVO.getPassword() != null) {
                employee.setPassword(employeeVO.getPassword());
            }
            if (employeeVO.getName() != null) {
                employee.setName(employeeVO.getName());
            }
            if (employeeVO.getTel() != null) {
                employee.setTel(employeeVO.getTel());
            }
            if (employeeVO.getPhone() != null) {
                employee.setPhone(employeeVO.getPhone());
            }
            if (employeeVO.getEmail() != null) {
                employee.setEmail(employeeVO.getEmail());
            }
            if (employeeVO.getStartDate() != null) {
                employee.setStartDate(employeeVO.getStartDate());
            }
            if (employeeVO.getSickLeaveHours() != null) {
                employee.setSickLeaveHours(employeeVO.getSickLeaveHours());
            }
            if (employeeVO.getPersonalLeaveHours() != null) {
                employee.setPersonalLeaveHours(employeeVO.getPersonalLeaveHours());
            }
            if (employeeVO.getAnnualLeaveHours() != null) {
                employee.setAnnualLeaveHours(employeeVO.getAnnualLeaveHours());
            }
            if (employeeVO.getBereavementLeaveHours() != null) {
                employee.setBereavementLeaveHours(employeeVO.getBereavementLeaveHours());
            }
            if (employeeVO.getMarriageLeaveHours() != null) {
                employee.setMarriageLeaveHours(employeeVO.getMarriageLeaveHours());
            }
            if (employeeVO.getMenstrualLeaveHours() != null) {
                employee.setMenstrualLeaveHours(employeeVO.getMenstrualLeaveHours());
            }
            if (employeeVO.getOfficialLeaveHours() != null) {
                employee.setOfficialLeaveHours(employeeVO.getOfficialLeaveHours());
            }
            if (employeeVO.getBranch() != null) {
                employee.setBranch(employeeVO.getBranch());
            }
            if (employeeVO.getEndDate() != null) {
                employee.setEndDate(employeeVO.getEndDate());
            }

            // 新增對 teamLeaderId 的處理
            if (employeeVO.getTeamLeaderId() != null) {
                Optional<Employee> teamLeaderOptional = employeeRepo.findById(employeeVO.getTeamLeaderId());
                if (teamLeaderOptional.isPresent()) {
                    employee.setTeamLeader(teamLeaderOptional.get());
                } else {
                    throw new RuntimeException("找不到指定的直属主管");
                }
            }
            employeeRepo.save(employee);
            EmployeeVO updateEmployeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, updateEmployeeVO);
            updateEmployeeVO.setTeamLeaderId(employee.getTeamLeader().getId());
            updateEmployeeVO.setTeamLeaderName(employee.getTeamLeader().getName());
            return updateEmployeeVO;
        } else {
            return null;
        }
    }
}
