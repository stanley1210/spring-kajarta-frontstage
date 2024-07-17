package com.spring_kajarta_frontstage.service.impl;


import com.kajarta.demo.dto.LeaveDTO;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Leave;
import com.kajarta.demo.vo.LeaveVO;
import com.spring_kajarta_frontstage.repository.EmployeeRepository;
import com.spring_kajarta_frontstage.repository.LeaveRepository;
import com.spring_kajarta_frontstage.service.LeaveService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//    @Override
//    public List<LeaveVO> multiConditionQuery(Integer leaveStatus, String startTime, String endTime, Integer leaveType,
//                                             Integer employee, Integer teamLeaderId, Integer permisionStatus, String validityPeriodStart,
//                                             String validityPeriodEnd) {
//        List<Leave> leaves = leaveRepo.findByMultipleConditions(leaveStatus, startTime, endTime, leaveType, employee,
//                teamLeaderId, permisionStatus, validityPeriodStart, validityPeriodEnd);
//        List<LeaveVO> leaveVOList = new ArrayList<>();
//        for (Leave leave : leaves) {
//            LeaveVO leaveVO = new LeaveVO();
//            BeanUtils.copyProperties(leave, leaveVO);
//
//            // 设置时间字段，先进行空值判断
//            leaveVO.setStartTime(leave.getStartTime() != null
//                    ? DatetimeConverter.toString(new Date(leave.getStartTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//            leaveVO.setEndTime(leave.getEndTime() != null
//                    ? DatetimeConverter.toString(new Date(leave.getEndTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//            leaveVO.setCreateTime(leave.getCreateTime() != null
//                    ? DatetimeConverter.toString(new Date(leave.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//            leaveVO.setUpdateTime(leave.getUpdateTime() != null
//                    ? DatetimeConverter.toString(new Date(leave.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//            leaveVO.setAuditTime(leave.getAuditTime() != null
//                    ? DatetimeConverter.toString(new Date(leave.getAuditTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//            leaveVO.setValidityPeriodStart(leave.getValidityPeriodStart() != null
//                    ? DatetimeConverter.toString(new Date(leave.getValidityPeriodStart().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//            leaveVO.setValidityPeriodEnd(leave.getValidityPeriodEnd() != null
//                    ? DatetimeConverter.toString(new Date(leave.getValidityPeriodEnd().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
//                    : null);
//
//            leaveVO.setEmployeeId(leave.getEmployee().getId());
//            leaveVOList.add(leaveVO);
//        }
//        return leaveVOList;
//    }
    public Page<LeaveVO> findByConditionsWithPagination(
            Integer leaveStatus,
            String startTime,
            String endTime,
            Integer leaveType,
            Integer employeeId,
            Integer teamLeaderId,
            Integer permisionStatus,
            String validityPeriodStart,
            String validityPeriodEnd,
            int page,
            int size,
            String sort,
            boolean dir) {

        Sort sortOrder = dir ? Sort.by(Sort.Direction.ASC, sort) : Sort.by(Sort.Direction.DESC, sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<LeaveDTO> leavePage = leaveRepo.findAllByMultipleConditions(leaveStatus, startTime, endTime, leaveType,
                employeeId, teamLeaderId, permisionStatus, validityPeriodStart, validityPeriodEnd, pageable);

        return leavePage.map(leave -> {
            LeaveVO leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);
            leaveVO.setStartTime(leave.getStartTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getStartTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setEndTime(leave.getEndTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getEndTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setCreateTime(leave.getCreateTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setUpdateTime(leave.getUpdateTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setAuditTime(leave.getAuditTime() != null
                    ? DatetimeConverter.toString(new Date(leave.getAuditTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setValidityPeriodStart(leave.getValidityPeriodStart() != null
                    ? DatetimeConverter.toString(new Date(leave.getValidityPeriodStart().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setValidityPeriodEnd(leave.getValidityPeriodEnd() != null
                    ? DatetimeConverter.toString(new Date(leave.getValidityPeriodEnd().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS)
                    : null);
            leaveVO.setEmployeeId(leave.getEmployeeId());
            return leaveVO;
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
