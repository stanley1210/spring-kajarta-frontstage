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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * 查詢單筆，依據請假id查詢單一請假資訊
     *
     * @param leaveId
     * @return LeaveVO
     */
    @Override
    public Leave findById(Integer leaveId) {
        Optional<Leave> leave = leaveRepo.findById(leaveId);
        return leave.orElse(null);
    }

    // 多條件查詢，依據假單的請假或給假狀態、開始時段、結束時段、假種、休假員工、核可主管、核可狀態、使用期限(開始)、使用期限(結束)
    @Override
    public List<LeaveVO> multiConditionQuery(Integer leaveStatus, Date startTime, Date endTime, Integer leaveType, Integer employee, Integer teamLeaderId, Integer permisionStatus, Date validityPeriodStart, Date validityPeriodEnd) {
        List<Leave> leaves = leaveRepo.findByMultipleConditions(
                leaveStatus, startTime, endTime, leaveType, employee, teamLeaderId, permisionStatus, validityPeriodStart, validityPeriodEnd);
        List<LeaveVO> leaveVOList = new ArrayList<>();
        for (Leave leave : leaves) {
            LeaveVO leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);
            leaveVOList.add(leaveVO);
        }
        return leaveVOList;
    }

    // 新增
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

    // 修改
    @Override
    public LeaveVO modify(LeaveVO leaveVO) {
        Optional<Leave> optionalLeave = leaveRepo.findById(leaveVO.getId());
        if (optionalLeave.isPresent()) {
            Leave leave = optionalLeave.get();
            BeanUtils.copyProperties(leaveVO, leave, "createTime", "updateTime");
            leaveRepo.save(leave);
            LeaveVO updateLeaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, updateLeaveVO);
            return updateLeaveVO;
        } else {
            return null;
        }

    }
}
