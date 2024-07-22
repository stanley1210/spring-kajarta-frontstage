package com.spring_kajarta_frontstage.controller;

import java.util.ArrayList;
import java.util.List;

import com.spring_kajarta_frontstage.service.ViewCarAssignedService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.ViewCarAssigned;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.ViewCarAssignedVO;
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

    @Operation(summary = "賞車指派表-依據賞車指派表id查詢單筆")
    @GetMapping("/{id}")
    public Result<ViewCarAssignedVO> info(@Parameter(description = "賞車指派表id") @PathVariable Integer id) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢賞車指派表資訊-單筆：{}", "到時候換成上一步拿到的管理員", id);
        ViewCarAssignedVO viewCarAssignedVO;
        try {
            ViewCarAssigned viewCarAssigned = viewCarAssignedService.findById(id);

            viewCarAssignedVO = viewCarAssignedService.vOChange(viewCarAssigned);

        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(viewCarAssignedVO);
    }

    // 多條件查詢 Spring版 + VO
    @Operation(summary = "賞車指派表-依據多條件查詢，含分頁查全部")
    @PostMapping("/findByHQL")
    public Result<List<ViewCarAssignedVO>> findByHQL(@Parameter(description = "賞車指派表查詢條件") @RequestBody String body) {
        // todo:依據多條件(JSON)
        // 條件 - id, viewCarDateStr, viewCarDateEnd, employeeId, teamLeaderId, viewCarId,
        // carId,
        // - carinfoId, modelid, assignedStatus
        // 分頁 - is_page, max, dir, order

        log.info("{}-後台查詢賞車指派表表資訊-多條件JSONE：{}", "到時候換成上一步拿到的管理員", body);
        Result<List<ViewCarAssignedVO>> result = new Result<List<ViewCarAssignedVO>>();
        List<ViewCarAssignedVO> viewCarAssignedVOs = new ArrayList<>();
        try {
            Page<ViewCarAssigned> pageViewCarAssigned = viewCarAssignedService.findByHQL(body);
            List<ViewCarAssigned> viewCarAssigneds = pageViewCarAssigned.getContent();

            if (viewCarAssigneds != null && !viewCarAssigneds.isEmpty()) {
                for (ViewCarAssigned viewCarAssigned : viewCarAssigneds) {

                    ViewCarAssignedVO viewCarAssignedVO = viewCarAssignedService.vOChange(viewCarAssigned);
                    viewCarAssignedVOs.add(viewCarAssignedVO);
                }
            }

            result.setData(viewCarAssignedVOs);
            result.setSuccess(true);
            result.setTotalPage(pageViewCarAssigned.getTotalPages());
            result.setTotalElement(pageViewCarAssigned.getTotalElements());
            result.setPageNumber(pageViewCarAssigned.getNumber());
            result.setNumberOfElementsOnPage(pageViewCarAssigned.getNumberOfElements());
            result.setHasNext(pageViewCarAssigned.hasNext());
            result.setHasPrevious(pageViewCarAssigned.hasPrevious());
            result.setFirstPageOrNot(pageViewCarAssigned.isFirst());
            result.setLastPageOrNot(pageViewCarAssigned.isLast());

        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return result;
    }

    // 新增一筆
    @Operation(summary = "賞車指派表-新增一筆 / 檢查viewCarId 的assignedStatus 有一張\"未指派(assignedStatus=0)\"就不可新建")
    @PostMapping("")
    public String create(@Parameter(description = "新增賞車指派表") @RequestBody String body) {
        JSONObject responseBody = new JSONObject();

        JSONObject obj = new JSONObject(body);
        Integer viewCarId = obj.isNull("viewCarId") ? null : obj.getInt("viewCarId");

        // 檢查viewCarId 的assignedStatus 有一張"未指派(assignedStatus=0)"就不可新建
        String checkViewCarIdAssignedStatusy = "{\"viewCarId\":" + viewCarId + ","
                + "\"assignedStatus\":\"0\"}";
        System.out.println(checkViewCarIdAssignedStatusy);
        Page<ViewCarAssigned> pageViewCarAssigned = viewCarAssignedService.findByHQL(checkViewCarIdAssignedStatusy);
        System.out.println(pageViewCarAssigned.getTotalElements());

        if (pageViewCarAssigned.getTotalElements() != 0) {
            responseBody.put("success", false);
            responseBody.put("message", "賞車ID : " + viewCarId + " 已有 [一張未排程] 資料 無法新增");
        } else {
            ViewCarAssigned viewCarAssigned = viewCarAssignedService.create(body);
            if (viewCarAssigned == null) {
                responseBody.put("success", false);
                responseBody.put("message", "賞車指派表新增失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "賞車指派表新增成功");
            }
        }
        return responseBody.toString();

    }

    // 修改一筆
    @Operation(summary = "賞車指派表-修改一筆 / 不做檢查")
    @PutMapping("/{id}")
    public Result<ViewCarAssignedVO> modify(@Parameter(description = "修改賞車指派表 ID") @PathVariable Integer id,
            @Parameter(description = "修改賞車指派表資料") @RequestBody String body) {

        if (id == null) {
            return ResultUtil.error("賞車指派表Id是必要欄位");
        } else {
            if (!viewCarAssignedService.exists(id)) {
                return ResultUtil.error("賞車指派表Id不存在");
            } else {
                ViewCarAssigned viewCarAssigned = viewCarAssignedService.modify(body);
                if (viewCarAssigned == null) {
                    return ResultUtil.error("賞車指派表修改失敗");
                } else {

                    ViewCarAssignedVO viewCarAssignedVO = viewCarAssignedService.vOChange(viewCarAssigned);

                    return ResultUtil.success(viewCarAssignedVO);
                }
            }
        }
    }
}
