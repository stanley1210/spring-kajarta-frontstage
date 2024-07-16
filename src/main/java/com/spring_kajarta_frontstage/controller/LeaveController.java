package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.Leave;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.LeaveVO;
import com.spring_kajarta_frontstage.service.LeaveService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "管理後台-請假")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/leave")
@Controller
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @Operation(summary = "統計會員數量")
    @GetMapping("/count")
    public Result<Long> countLeaves() {
        long count = leaveService.countLeaves();
        return ResultUtil.success(count);
    }

    @Operation(summary = "請假資訊-查詢全部")
    @GetMapping("/all")
    public Result<List<LeaveVO>> findAll() {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢請假資訊-全部", "到時候換成上一步拿到的管理員");
        List<LeaveVO> leaveVOList;
        try {
            leaveVOList = leaveService.findAll();
            log.info("查詢到 {} 條請假數據", leaveVOList.size());
        } catch (Exception e) {
            log.error("查詢出錯", e);
            return ResultUtil.error("查詢出錯");
        }
        return ResultUtil.success(leaveVOList);
    }


    @Operation(summary = "請假資訊-依據請假id查詢單筆")
    @GetMapping("/info/{leaveId}")
    public Result<LeaveVO> info(@Parameter(description = "請假id") @PathVariable Integer leaveId) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢請假資訊-單筆：{}", "到時候換成上一步拿到的管理員", leaveId);
        LeaveVO leaveVO;
        try {
            Leave leave = leaveService.findById(leaveId);
            if (leave == null) {
                return ResultUtil.error("查無此請假資訊");
            }

            leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);
            leaveVO.setEmployeeId(leave.getEmployee().getId());

            // 设置时间字段，先进行空值判断
            leaveVO.setCreateTime(leave.getCreateTime() != null ? DatetimeConverter.toString(new Date(leave.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
            leaveVO.setUpdateTime(leave.getUpdateTime() != null ? DatetimeConverter.toString(new Date(leave.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
            leaveVO.setStartTime(leave.getStartTime() != null ? DatetimeConverter.toString(new Date(leave.getStartTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
            leaveVO.setEndTime(leave.getEndTime() != null ? DatetimeConverter.toString(new Date(leave.getEndTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
            leaveVO.setAuditTime(leave.getAuditTime() != null ? DatetimeConverter.toString(new Date(leave.getAuditTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
            leaveVO.setValidityPeriodStart(leave.getValidityPeriodStart() != null ? DatetimeConverter.toString(new Date(leave.getValidityPeriodStart().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
            leaveVO.setValidityPeriodEnd(leave.getValidityPeriodEnd() != null ? DatetimeConverter.toString(new Date(leave.getValidityPeriodEnd().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS) : null);
        } catch (Exception e) {
            log.error("查詢出錯", e);
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(leaveVO);
    }
    @Operation(summary = "請假資訊-查詢多筆，依據假單的請假或給假狀態、開始時段、結束時段、假種、休假員工、核可主管、核可狀態、使用期限(開始)、使用期限(結束)")
    @PostMapping("/multi")
    public Result<List<LeaveVO>> multiConditionQuery(@RequestBody LeaveVO leaveVO) {
        Integer leaveStatus = leaveVO.getLeaveStatus();
        String startTime = leaveVO.getStartTime();
        String endTime = leaveVO.getEndTime();
        Integer leaveType = leaveVO.getLeaveType();
        Integer employee = leaveVO.getEmployeeId();
        Integer teamLeaderId = leaveVO.getTeamLeaderId();
        Integer permisionStatus = leaveVO.getPermisionStatus();
        String validityPeriodStart = leaveVO.getValidityPeriodStart();
        String validityPeriodEnd = leaveVO.getValidityPeriodEnd();

        log.info("後台查詢員工資訊-多筆：leaveStatus: {} startTime: {} endTime: {} leaveType: {} employee: {} teamLeaderId: {} permisionStatus: {} validityPeriodStart: {} validityPeriodEnd: {}",
                leaveStatus, startTime, endTime, leaveType, employee, teamLeaderId, permisionStatus, validityPeriodStart, validityPeriodEnd);
        try {
            List<LeaveVO> leaveVOList = leaveService.multiConditionQuery(leaveStatus, startTime, endTime, leaveType, employee, teamLeaderId, permisionStatus, validityPeriodStart, validityPeriodEnd);
            return ResultUtil.success(leaveVOList);
        } catch (Exception e) {
            log.error("查詢出錯", e);
            return ResultUtil.error("查詢出錯");
        }
    }


    @Operation(summary = "請假資訊-新增假單")
    @PostMapping(value = "/add")
    public Result<LeaveVO> addLeave(@RequestBody LeaveVO leaveVO) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-新增客戶資訊：{}", "到時候換成上一步拿到的管理員", leaveVO.toString());
        try {
            leaveVO = leaveService.create(leaveVO);
        } catch (Exception e) {
            log.error("Error while creating Leave", e);
            return ResultUtil.error("新增假單出錯");
        }
        return ResultUtil.success(leaveVO);
    }

    @Operation(summary = "請假資訊-修改假單")
    @PutMapping(value = "/modify/{leaveId}")
    public Result<LeaveVO> modifyLeave(
            @Parameter(description = "假單Id") @PathVariable Integer leaveId,
            @RequestBody LeaveVO leaveVO) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-修改假單資訊：{}", "到時候換成上一步拿到的管理員", leaveVO.toString());
        try {
            LeaveVO updateLeave = leaveService.modify(leaveVO);
            if (updateLeave == null) {
                return ResultUtil.error("找不到假單Id: " + leaveId);
            }
            return ResultUtil.success(updateLeave);
        } catch (Exception e) {
            return ResultUtil.error("修改假單出錯");
        }


    }

}


