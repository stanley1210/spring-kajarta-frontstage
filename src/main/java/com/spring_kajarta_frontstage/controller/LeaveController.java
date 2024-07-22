package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.enums.LeaveTypeEnum;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Leave;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.LeaveVO;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.service.LeaveService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理後台-請假")
@CrossOrigin
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/leave")
@Controller
public class LeaveController extends BaseController {
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private EmployeeService employeeService;

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

            leaveVO.setLeaveTypeName(LeaveTypeEnum.getByCode(leave.getLeaveType()).getLeaveType());


            leaveVO.setEmployeeId(leave.getEmployee().getId());
            leaveVO.setEmployeeName(leave.getEmployee().getName());

            leaveVO.setStartTime(DatetimeConverter.toString(leave.getStartTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            leaveVO.setEndTime(DatetimeConverter.toString(leave.getEndTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            leaveVO.setCreateTime(DatetimeConverter.toString(leave.getCreateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            leaveVO.setUpdateTime(DatetimeConverter.toString(leave.getUpdateTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            leaveVO.setAuditTime(DatetimeConverter.toString(leave.getAuditTime(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            leaveVO.setValidityPeriodStart(DatetimeConverter.toString(leave.getValidityPeriodStart(), DatetimeConverter.YYYY_MM_DD_HH_MM));
            leaveVO.setValidityPeriodEnd(DatetimeConverter.toString(leave.getValidityPeriodEnd(), DatetimeConverter.YYYY_MM_DD_HH_MM));


            // 依據代理人id去查Employee
            Employee employeeDeputy = employeeService.findById(leave.getDeputyId());
            leaveVO.setDeputyId(employeeDeputy.getId());
            leaveVO.setDeputyName(employeeDeputy.getName());



        } catch (Exception e) {
            log.error("查詢出錯", e);
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(leaveVO);
    }


    @Operation(summary = "假單資訊-依多條件查詢(分頁)")
    @PostMapping("/query")
    public Result<Page<LeaveVO>> queryLeaves(@RequestBody LeaveVO leaveVO, HttpServletRequest request) {
        // 解析token todo
        System.out.println("id="+getAdminId());
        System.out.println("name="+getAdmin());
        log.info("{}-後台查詢假單資訊-多條件查詢(分頁)", getAdmin());

        Page<LeaveVO> leavePage = leaveService.findByConditionsWithPagination(leaveVO);

        Result<Page<LeaveVO>> result = new Result<>();
        result.setCode(200);
        result.setMsg("查詢成功");
        result.setData(leavePage);
        result.setSuccess(true);
        return result;
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


