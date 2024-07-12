package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.CarAdjust;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CarAdjustVO;
import com.spring_kajarta_frontstage.service.CarAdjustService;
import com.spring_kajarta_frontstage.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // @Autowired
    // private CarAdjustBeanToVO carAdjustBeanToVO;

    @Operation(summary = "調整簽核列表-依據調整簽核id查詢單筆")
    @GetMapping("/{id}")
    public Result<CarAdjustVO> info(@Parameter(description = "調整簽核id") @PathVariable Integer id) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢調整簽核列資訊-單筆：{}", "到時候換成上一步拿到的管理員", id);
        CarAdjustVO carAdjustVO;
        try {
            CarAdjust carAdjust = carAdjustService.findById(id);
            carAdjustVO = carAdjustService.vOChange(carAdjust);

        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(carAdjustVO);
    }

    // 多條件查詢 Spring版 + VO
    @Operation(summary = "調整簽核列表-依據多條件查詢，含分頁查全部")
    @PostMapping("/findByHQL")
    public Result<List<CarAdjustVO>> findByHQL(@Parameter(description = "調整簽核查詢條件") @RequestBody String body) {
        // todo:依據多條件(JSON)
        // 條件 - id, teamLeaderId, employeeId, carId, approvalStatus, approvalType,
        // - floatingAmountMax, floatingAmountMin, createTime, updateTime,
        // 分頁 - is_page, max, dir, order

        log.info("{}-後台查詢調整簽核列表資訊-多條件JSONE：{}", "到時候換成上一步拿到的管理員", body);
        Result<List<CarAdjustVO>> result = new Result<List<CarAdjustVO>>();
        List<CarAdjustVO> carAdjustVOs = new ArrayList<>();
        try {
            Page<CarAdjust> pageCarAdjust = carAdjustService.findByHQL(body);
            List<CarAdjust> carAdjusts = pageCarAdjust.getContent();

            if (carAdjusts != null && !carAdjusts.isEmpty()) {
                for (CarAdjust carAdjust : carAdjusts) {

                    CarAdjustVO carAdjustVO = carAdjustService.vOChange(carAdjust);
                    carAdjustVOs.add(carAdjustVO);
                }
            }

            result.setData(carAdjustVOs);
            result.setSuccess(true);
            result.setTotalPage(pageCarAdjust.getTotalPages());
            result.setTotalElement(pageCarAdjust.getTotalElements());
            result.setPageNumber(pageCarAdjust.getNumber());
            result.setNumberOfElementsOnPage(pageCarAdjust.getNumberOfElements());
            result.setHasNext(pageCarAdjust.hasNext());
            result.setHasPrevious(pageCarAdjust.hasPrevious());
            result.setFirstPageOrNot(pageCarAdjust.isFirst());
            result.setLastPageOrNot(pageCarAdjust.isLast());

        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return result;
    }
}
