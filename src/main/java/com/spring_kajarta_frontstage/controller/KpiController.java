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
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Kpi;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.KpiVO;
import com.spring_kajarta_frontstage.service.KpiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "管理後台-Kpi")
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kpi")
public class KpiController {

    @Autowired
    private KpiService kpiService;

    @Operation(summary = "KPI-依據KPI id查詢單筆")
    @GetMapping("/{id}")
    public Result<KpiVO> info(@Parameter(description = "KPI id") @PathVariable Integer id) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢Kpi列表資訊-單筆：{}", "到時候換成上一步拿到的管理員", id);
        KpiVO kpiVO;
        try {
            Kpi kpi = kpiService.findById(id);

            kpiVO = new KpiVO();
            BeanUtils.copyProperties(kpi, kpiVO);

            // 主管 teamleader
            Employee teamleader = kpi.getEmployee().getTeamLeader();
            kpiVO.setTeamLeaderName(teamleader.getName());
            kpiVO.setEmployeeName(kpi.getEmployee().getName());
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(kpiVO);
    }

}
