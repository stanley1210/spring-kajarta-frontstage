package com.spring_kajarta_frontstage.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.ViewCarAssigned;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.ViewCarAssignedVO;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.service.ViewCarAssignedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "管理後台-賞車指派表")
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/viewCarAssigned")
public class ViewCarAssignedController {

    @Autowired
    private ViewCarAssignedService viewCarAssignedService;

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "賞車指派表-依據賞車指派表id查詢單筆")
    @GetMapping("/{id}")
    public Result<ViewCarAssignedVO> info(@Parameter(description = "賞車指派表id") @PathVariable Integer id) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢賞車指派表資訊-單筆：{}", "到時候換成上一步拿到的管理員", id);
        ViewCarAssignedVO viewCarAssignedVO;
        try {
            ViewCarAssigned viewCarAssigned = viewCarAssignedService.findById(id);

            viewCarAssignedVO = new ViewCarAssignedVO();
            BeanUtils.copyProperties(viewCarAssigned, viewCarAssignedVO);

            switch (viewCarAssigned.getAssignedStatus()) {
                case 0:
                    viewCarAssignedVO.setAssignedStatusName("未指派");
                    break;
                case 1:
                    viewCarAssignedVO.setAssignedStatusName("已指派");
                    break;
                case 2:
                    viewCarAssignedVO.setAssignedStatusName("註銷");
                    break;

                default:
                    viewCarAssignedVO.setAssignedStatusName(
                            "簽核狀態錯誤 viewCarAssigned = " + viewCarAssigned.getAssignedStatus().toString());
            }

            viewCarAssignedVO.setViewCarId(viewCarAssigned.getViewCar().getId());
            viewCarAssignedVO.setTeamLeaderName(employeeService.findById(viewCarAssigned.getTeamLeaderId()).getName());
            viewCarAssignedVO.setEmployeeName(viewCarAssigned.getEmployee().getName());
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(viewCarAssignedVO);
    }
}
