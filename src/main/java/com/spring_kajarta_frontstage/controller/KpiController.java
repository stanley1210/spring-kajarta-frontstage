package com.spring_kajarta_frontstage.controller;

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

import com.kajarta.demo.domian.Result;
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

            kpiVO = kpiService.vOChange(kpi);
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(kpiVO);
    }

    // 多條件查詢 Spring版 + VO
    @Operation(summary = "KPI-依據多條件查詢，含分頁查全部")
    @PostMapping("/findByHQL")
    public Result<List<KpiVO>> findByHQL(@Parameter(description = "KPI查詢條件") @RequestBody String body) {
        // todo:依據多條件(JSON)
        // 條件 - id, selectStrDay, selectEndDay, employeeId, teamLeaderId,
        // - teamLeaderRatingMax, teamLeaderRatingMin, salesScoreMax, salesScoreMin,
        // totalScoreMax, totalScoreMin
        // 分頁 - is_page, max, dir, order

        log.info("{}-後台查詢調整簽核列表資訊-多條件JSONE：{}", "到時候換成上一步拿到的管理員", body);
        Result<List<KpiVO>> result = new Result<List<KpiVO>>();
        List<KpiVO> kpiVOs = new ArrayList<>();
        try {
            Page<Kpi> pageKpi = kpiService.findByHQL(body);
            List<Kpi> kpis = pageKpi.getContent();

            if (kpis != null && !kpis.isEmpty()) {
                for (Kpi kpi : kpis) {

                    KpiVO kpiVO = kpiService.vOChange(kpi);
                    kpiVOs.add(kpiVO);
                }
            }

            result.setData(kpiVOs);
            result.setSuccess(true);
            result.setTotalPage(pageKpi.getTotalPages());
            result.setTotalElement(pageKpi.getTotalElements());
            result.setPageNumber(pageKpi.getNumber());
            result.setNumberOfElementsOnPage(pageKpi.getNumberOfElements());
            result.setHasNext(pageKpi.hasNext());
            result.setHasPrevious(pageKpi.hasPrevious());
            result.setFirstPageOrNot(pageKpi.isFirst());
            result.setLastPageOrNot(pageKpi.isLast());

        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return result;
    }

}
