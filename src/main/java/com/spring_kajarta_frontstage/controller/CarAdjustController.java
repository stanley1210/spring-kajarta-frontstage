package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.CarAdjust;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CarAdjustVO;
import com.spring_kajarta_frontstage.service.CarAdjustService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理後台-調整簽核列")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/carAdjust")
public class CarAdjustController {

    @Autowired
    private CarAdjustService carAdjustService;

    @Operation(summary = "調整簽核列-依據調整簽核列id查詢單筆")
    @GetMapping("/")
    public Result<CarAdjustVO> info(@Parameter(description = "調整簽核列id") Integer id) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢調整簽核列資訊-單筆：{}", "到時候換成上一步拿到的管理員", id);
        CarAdjustVO carAdjustVO;
        try {
            CarAdjust carAdjust = carAdjustService.findById(id);
            carAdjustVO = new CarAdjustVO();
            BeanUtils.copyProperties(carAdjust, carAdjustVO);
            carAdjustVO.setEmployeeId(carAdjust.getEmployee().getId());
            carAdjustVO.setCarId(carAdjust.getCar().getId());
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(carAdjustVO);
    }
}
