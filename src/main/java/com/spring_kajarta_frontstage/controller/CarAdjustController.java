package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.CarAdjust;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CarAdjustVO;
import com.spring_kajarta_frontstage.service.CarAdjustService;
import com.spring_kajarta_frontstage.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理後台-調整簽核列")
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/carAdjust")
public class CarAdjustController {

    @Autowired
    private CarAdjustService carAdjustService;

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "調整簽核列表-依據調整簽核id查詢單筆")
    @GetMapping("/{id}")
    public Result<CarAdjustVO> info(@Parameter(description = "調整簽核id") @PathVariable Integer id) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢調整簽核列資訊-單筆：{}", "到時候換成上一步拿到的管理員", id);
        CarAdjustVO carAdjustVO;
        try {
            CarAdjust carAdjust = carAdjustService.findById(id);

            carAdjustVO = new CarAdjustVO();
            BeanUtils.copyProperties(carAdjust, carAdjustVO);

            // 主管 teamleader
            Employee teamleader = employeeService.findById(carAdjust.getTeamLeaderId());

            // 簽核狀態 ApprovalStatus
            switch (carAdjust.getApprovalStatus()) {
                case 0:
                    carAdjustVO.setApprovalStatusName("待簽");
                    break;
                case 1:
                    carAdjustVO.setApprovalStatusName("已簽");
                    break;
                case 2:
                    carAdjustVO.setApprovalStatusName("拒絕");
                    break;

                default:
                    carAdjustVO.setApprovalStatusName(
                            "簽核狀態錯誤 ApprovalStatus = " + carAdjust.getApprovalStatus().toString());
            }

            // 簽核種類 ApprovalType
            switch (carAdjust.getApprovalType()) {
                case 0:
                    carAdjustVO.setApprovalTypeName("降價");
                    break;
                case 1:
                    carAdjustVO.setApprovalTypeName("漲價");
                    break;
                case 2:
                    carAdjustVO.setApprovalTypeName("下架");
                    break;

                default:
                    carAdjustVO.setApprovalTypeName("簽核種類錯誤 ApprovalType = " + carAdjust.getApprovalType().toString());
            }

            carAdjustVO.setTeamLeaderName(teamleader.getName());
            carAdjustVO.setEmployeeName(carAdjust.getEmployee().getName());
            carAdjustVO.setCarId(carAdjust.getCar().getId());
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(carAdjustVO);
    }
}
