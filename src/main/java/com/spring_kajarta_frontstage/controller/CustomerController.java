package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CustomerVO;
import com.spring_kajarta_frontstage.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "管理後台-會員")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Operation(summary = "會員資訊-依據會員id查詢單筆")
    @GetMapping("/info")
    public Result<CustomerVO> info(@Parameter(description = "會員id") Integer customerId){
        // todo:依據token獲取後台登入用戶


        log.info("{}-後台查詢客戶資訊-單筆：{}", "到時候換成上一步拿到的管理員", customerId);
        CustomerVO customerVo = null;
        try {
            customerVo = customerService.findById(customerId);
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }
        return ResultUtil.success(customerVo);
    }


}
