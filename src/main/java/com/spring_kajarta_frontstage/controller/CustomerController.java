package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CustomerVO;
import com.spring_kajarta_frontstage.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/info/{customerId}")
    public Result<CustomerVO> info(@Parameter(description = "會員id") @PathVariable Integer customerId) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢客戶資訊-單筆：{}", "到時候換成上一步拿到的管理員", customerId);
        CustomerVO customerVO;
        try {
            Customer customer = customerService.findById(customerId);
            customerVO = new CustomerVO();
            BeanUtils.copyProperties(customer, customerVO);
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(customerVO);
    }


    @Operation(summary = "會員資訊-查詢多筆，依據用戶性別、帳號分類、帳號、城市、姓名、手機、電子信箱")
    @PostMapping("/multi")
    public Result<List<CustomerVO>> multiConditionQuery(@RequestBody CustomerVO customerVO) {
        Character sex = customerVO.getSex();
        Integer accountType = customerVO.getAccountType();
        String account = customerVO.getAccount();
        Integer city = customerVO.getCity();
        String name = customerVO.getName();
        String phone = customerVO.getPhone();
        String email = customerVO.getEmail();

        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢客戶資訊-多筆：{}", "到時候換成上一步拿到的管理員", "sex: " + sex + " accountType: " + accountType + " city: " + city + " name: " + name);

        try {
            List<CustomerVO> customerVOList = customerService.multiConditionQuery(sex, accountType, account, city, name, phone , email);
            return ResultUtil.success(customerVOList);
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }
    }


    @Operation(summary = "會員資訊-新增會員")
    @PostMapping(value = "/add")
    public Result<CustomerVO> addCustomer(@RequestBody CustomerVO customerVO) {
        // todo:依據token獲取後台登入用戶


        log.info("{}-新增客戶資訊：{}", "到時候換成上一步拿到的管理員", customerVO.toString());
        try {
            customerVO = customerService.create(customerVO);
        } catch (Exception e) {
            return ResultUtil.error("新增用戶出錯");
        }
        return ResultUtil.success(customerVO);
    }

    @Operation(summary = "會員資訊-修改會員")
    @PutMapping(value = "/modify/{customerId}")
    public Result<CustomerVO> modifyCustomer(
            @Parameter(description = "會員id") @PathVariable Integer customerId,
            @RequestBody CustomerVO customerVO) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-修改客戶資訊：{}", "到時候換成上一步拿到的管理員", customerVO.toString());
        customerVO.setId(customerId); // 確保傳入的客戶資料有正確的ID
        try {
            CustomerVO updatedCustomer = customerService.modify(customerVO);
            if (updatedCustomer == null) {
                return ResultUtil.error("找不到會員ID: " + customerId);
            }
            return ResultUtil.success(updatedCustomer);
        } catch (Exception e) {
            return ResultUtil.error("修改用戶出錯");
        }
    }


}
