package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.Leave;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.LeaveVO;
import com.spring_kajarta_frontstage.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "請假資訊-依據請假id查詢單筆")
    @GetMapping("/info/{leaveId}")
    public Result<LeaveVO> info(@Parameter(description = "請假id") @PathVariable Integer leaveId){
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢請假資訊-單筆：{}", "到時候換成上一步拿到的管理員", leaveId);
        LeaveVO leaveVO;
        try {
            Leave leave = leaveService.findById(leaveId);
            leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);
            leaveVO.setEmployeeId(leave.getEmployee().getId());
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(leaveVO);
    }

    @Operation(summary = "請假資訊-新增假單")
    @PostMapping(value = "/add")
    public Result<LeaveVO> addLeave(@RequestBody LeaveVO leaveVO){
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

}


