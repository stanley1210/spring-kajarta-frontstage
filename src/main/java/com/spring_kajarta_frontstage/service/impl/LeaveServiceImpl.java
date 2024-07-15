package com.spring_kajarta_frontstage.service.impl;


import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Leave;
import com.kajarta.demo.vo.LeaveVO;
import com.spring_kajarta_frontstage.repository.EmployeeRepository;
import com.spring_kajarta_frontstage.repository.LeaveRepository;
import com.spring_kajarta_frontstage.service.LeaveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private EmployeeRepository employeeRepo;
    /**
     * 查詢單筆，依據請假id查詢單一請假資訊
     * @param leaveId
     * @return LeaveVO
     */
    @Override
    public Leave findById(Integer leaveId) {
        Optional<Leave> leave = leaveRepo.findById(leaveId);
        return leave.orElse(null);
    }

    @Override
    public LeaveVO create(LeaveVO leaveVO) {
        Leave leave = new Leave();
        BeanUtils.copyProperties(leaveVO, leave);
        // 查詢並設置employee
        Optional<Employee> employeeOptional = employeeRepo.findById(leaveVO.getEmployeeId());
        if (!employeeOptional.isPresent()) {
            throw new RuntimeException("找不到此員工");
        }
        leave.setEmployee(employeeOptional.get());
        leaveRepo.save(leave);
        LeaveVO leaveVONew = new LeaveVO();
        BeanUtils.copyProperties(leaveVO, leaveVONew);
        return leaveVONew;
    }
}
