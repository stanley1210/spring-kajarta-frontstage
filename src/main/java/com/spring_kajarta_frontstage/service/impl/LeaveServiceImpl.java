package com.spring_kajarta_frontstage.service.impl;


import com.kajarta.demo.dto.LeaveDTO;
import com.kajarta.demo.enums.LeaveTypeEnum;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Leave;
import com.kajarta.demo.vo.LeaveVO;
import com.spring_kajarta_frontstage.repository.EmployeeRepository;
import com.spring_kajarta_frontstage.repository.LeaveRepository;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.service.LeaveService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private EmployeeService employeeService;


    @Override
    public long countLeaves() {
        return leaveRepo.count();
    }

    // 查詢全部
    @Override
    public List<LeaveVO> findAll() {
        List<Leave> leaves = leaveRepo.findAll();
        log.info("查詢到 {} 條請假數據", leaves.size());

        List<LeaveVO> leaveVOList = new ArrayList<>();
        for (Leave leave : leaves) {
            LeaveVO leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);

            leaveVO.setStartTime(DatetimeConverter.toString(leave.getStartTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            leaveVO.setEndTime(DatetimeConverter.toString(leave.getEndTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            leaveVO.setCreateTime(DatetimeConverter.toString(leave.getCreateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            leaveVO.setUpdateTime(DatetimeConverter.toString(leave.getUpdateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            leaveVO.setAuditTime(DatetimeConverter.toString(leave.getAuditTime(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            leaveVO.setValidityPeriodStart(DatetimeConverter.toString(leave.getValidityPeriodStart(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            leaveVO.setValidityPeriodEnd(DatetimeConverter.toString(leave.getValidityPeriodEnd(), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));

            leaveVO.setEmployeeId(leave.getEmployee().getId());

            leaveVOList.add(leaveVO);
        }

        log.info("轉換後的 LeaveVO 數量為 {}", leaveVOList.size());
        return leaveVOList;
    }

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

    public Page<LeaveVO> findByConditionsWithPagination(LeaveVO leaveVO) {
        Pageable pageable = PageRequest.of(leaveVO.getPageNum(), leaveVO.getPageSize());


        Page<LeaveDTO> leavePage = leaveRepo.findAllByMultipleConditions(
                leaveVO.getLeaveStatus(),
                leaveVO.getStartTime(),
                leaveVO.getEndTime(),
                leaveVO.getLeaveType(),
                leaveVO.getEmployeeId(),
                leaveVO.getTeamLeaderId(),
                leaveVO.getPermisionStatus(),
                leaveVO.getValidityPeriodStart(),
                leaveVO.getValidityPeriodEnd(),
                pageable);

        return leavePage.map(leave -> {
            LeaveVO leaveVONew = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVONew);
            leaveVONew.setLeaveTypeName(LeaveTypeEnum.getByCode(leave.getLeaveType()).getLeaveType());

            leaveVONew.setStartTime(leave.getStartTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getStartTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM)
                    : null);
            leaveVONew.setEndTime(leave.getEndTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getEndTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM)
                    : null);
            leaveVONew.setCreateTime(leave.getCreateTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM)
                    : null);
            leaveVONew.setUpdateTime(leave.getUpdateTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVONew.setAuditTime(leave.getAuditTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getAuditTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVONew.setValidityPeriodStart(leave.getValidityPeriodStart() != null
                    ? DatetimeConverter.toString(new Date(leave.getValidityPeriodStart().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM)
                    : null);
            leaveVONew.setValidityPeriodEnd(leave.getValidityPeriodEnd() != null
                    ? DatetimeConverter.toString(new Date(leave.getValidityPeriodEnd().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM)
                    : null);
            leaveVONew.setEmployeeId(leave.getEmployeeId());

            leaveVONew.setEmployeeName(leave.getEmployeeName());


            return leaveVONew;
        });
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





    @Override
    public LeaveVO modify(LeaveVO leaveVO) {
        Optional<Leave> optionalLeave = leaveRepo.findById(leaveVO.getId());
        if (optionalLeave.isPresent()) {
            Leave leave = optionalLeave.get();

            // 使用 getter 和 setter 方法更新屬性
            if (leaveVO.getLeaveStatus() != null) {
                // 检查是否发生了状态变化
                if (leave.getPermisionStatus() == null || !leaveVO.getPermisionStatus().equals(leave.getPermisionStatus())) {
                    leave.setAuditTime(new Date()); // 设置当前时间到 auditTime
                }
                leave.setPermisionStatus(leaveVO.getPermisionStatus());
            }
            if (leaveVO.getStartTime() != null) {
                leave.setStartTime(DatetimeConverter.parse(leaveVO.getStartTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }
            if (leaveVO.getEndTime() != null) {
                leave.setEndTime(DatetimeConverter.parse(leaveVO.getEndTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }
            if (leaveVO.getLeaveType() != null) {
                leave.setLeaveType(leaveVO.getLeaveType());
            }
            if (leaveVO.getDeputyId() != null) {
                leave.setDeputyId(leaveVO.getDeputyId());
            }
            if (leaveVO.getTeamLeaderId() != null) {
                leave.setTeamLeaderId(leaveVO.getTeamLeaderId());
            }
            if (leaveVO.getPermisionRemarks() != null) {
                leave.setPermisionRemarks(leaveVO.getPermisionRemarks());
            }
            if (leaveVO.getAuditTime() != null) {
                leave.setAuditTime(DatetimeConverter.parse(leaveVO.getAuditTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }
            if (leaveVO.getReason() != null) {
                leave.setReason(leaveVO.getReason());
            }
            if (leaveVO.getActualLeaveHours() != null) {
                leave.setActualLeaveHours(leaveVO.getActualLeaveHours());
            }
            if (leaveVO.getImage() != null) {
                leave.setImage(leaveVO.getImage());
            }
            if (leaveVO.getSpecialLeaveHours() != null) {
                leave.setSpecialLeaveHours(leaveVO.getSpecialLeaveHours());
            }
            if (leaveVO.getCreateTime() != null) {
                leave.setCreateTime(DatetimeConverter.parse(leaveVO.getCreateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }
            if (leaveVO.getUpdateTime() != null) {
                leave.setUpdateTime(DatetimeConverter.parse(leaveVO.getUpdateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }
            if (leaveVO.getValidityPeriodStart() != null) {
                leave.setValidityPeriodStart(DatetimeConverter.parse(leaveVO.getValidityPeriodStart(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }
            if (leaveVO.getValidityPeriodEnd() != null) {
                leave.setValidityPeriodEnd(DatetimeConverter.parse(leaveVO.getValidityPeriodEnd(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            }

            // 保存修改后的假单
            leaveRepo.save(leave);

            // 将 Leave 对象转换回 LeaveVO
            LeaveVO updateLeaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, updateLeaveVO);
            return updateLeaveVO;
        } else {
            return null;
        }
    }


}
