package com.spring_kajarta_frontstage.controller;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.CustomerRecord;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CustomerRecordVO;
import com.spring_kajarta_frontstage.service.CustomerRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "前台-客戶紀錄")
@Slf4j
@Validated
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customerRecord")
public class CustomerRecordController {

    @Autowired
    private CustomerRecordService customerRecordService;

    @Operation(summary = "客戶紀錄-依據customer id查詢單筆")
    @GetMapping("/{id}")
    public Result<CustomerRecordVO> info(@Parameter(description = "customer id") @PathVariable Integer id) {
        // todo:依據customer id獲取紀錄

        log.info("{}-依據customer id查詢客戶紀錄-單筆：{}", "到時候換成上一步拿到的管理員", id);
        CustomerRecordVO customerRecordVO;
        try {
            CustomerRecord customerRecord = customerRecordService.findById(id);

            customerRecordVO = customerRecordService.vOChange(customerRecord);
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(customerRecordVO);
    }

    // 新增一筆
    @Operation(summary = "客戶紀錄-新增一筆 / 檢查customer id 是否有建過")
    @PostMapping("")
    public String create(@Parameter(description = "新增客戶紀錄") @RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");
        if (customerId != null) {
            CustomerRecord check = customerRecordService.findById(customerId);
            if (check == null) {
                CustomerRecord customerRecord = customerRecordService.create(body);
                if (customerRecord == null) {
                    responseBody.put("success", false);
                    responseBody.put("message", "CustomerRecord新增失敗(串接問題)");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "CustomerRecord新增成功");
                }
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "CustomerRecord新增失敗(ID已有紀錄)");
            }
        } else {
            responseBody.put("success", false);
            responseBody.put("message", "CustomerRecord新增失敗(沒有customer ID)");
        }
        return responseBody.toString();

    }

    // 修改一筆
    @Operation(summary = "客戶紀錄-修改一筆 / 不做檢查")
    @GetMapping("/modify/{id}")
    public Result<CustomerRecordVO> modify(
            @Parameter(description = "修改檢查customer ID") @PathVariable Integer id,
            @RequestParam(required = false) BigDecimal productionYear,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) BigDecimal milage,
            @RequestParam(required = false) BigDecimal score,
            @RequestParam(required = false) BigDecimal hp,
            @RequestParam(required = false) BigDecimal torque,
            @RequestParam(required = false) Integer brand,
            @RequestParam(required = false) Integer suspension,
            @RequestParam(required = false) Integer door,
            @RequestParam(required = false) Integer passenger,
            @RequestParam(required = false) Integer rearwheel,
            @RequestParam(required = false) Integer gasoline,
            @RequestParam(required = false) Integer transmission,
            @RequestParam(required = false) Integer cc) {

        if (id == null) {
            return ResultUtil.error("customer ID是必要欄位");
        } else {
            if (customerRecordService.findById(id) == null) {
                return ResultUtil.error("customer ID不存在");
            } else {
                CustomerRecord customerRecord = customerRecordService.modify(id, productionYear, price, milage,
                        score, hp, torque, brand, suspension, door, passenger, rearwheel, gasoline, transmission, cc);
                if (customerRecord == null) {
                    return ResultUtil.error("CustomerRecord 修改失敗");
                } else {

                    CustomerRecordVO customerRecordVO = customerRecordService.vOChange(customerRecord);

                    return ResultUtil.success(customerRecordVO);
                }
            }
        }
    }
}
