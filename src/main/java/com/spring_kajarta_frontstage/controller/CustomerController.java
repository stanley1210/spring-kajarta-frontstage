package com.spring_kajarta_frontstage.controller;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CustomerVO;
import com.spring_kajarta_frontstage.service.CustomerService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Operation(summary = "統計會員數量")
    @GetMapping("/count")
    public Result<Long> countCustomers() {
        long count = customerService.countCustomers();
        return ResultUtil.success(count);
    }

    @Operation(summary = "會員資訊-查詢全部")
    @GetMapping("/all")
    public Result<List<CustomerVO>> findAll() {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢客戶資訊-全部", "到時候換成上一步拿到的管理員");
        List<CustomerVO> customerVOList;
        try {
            customerVOList = customerService.findAll();
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }
        return ResultUtil.success(customerVOList);
    }


    @Operation(summary = "會員資訊-依多條件查詢(分頁)")
    @PostMapping("/query")
    public Result<Page<CustomerVO>> query(@RequestBody CustomerVO customerVO,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sort,
                                          @RequestParam(defaultValue = "true") boolean dir) {

        log.info("{}-後台查詢客戶資訊-多條件查詢(分頁)", "到時候換成上一步拿到的管理員");
        Page<CustomerVO> customerPage = customerService.findByConditionsWithPagination(
                customerVO.getSex(), customerVO.getAccountType(), customerVO.getAccount(), customerVO.getCity(),
                customerVO.getName(), customerVO.getPhone(), customerVO.getEmail(), page, size, sort, dir);

        Result<Page<CustomerVO>> result = new Result<>();
        result.setCode(200);
        result.setMsg("查詢成功");
        result.setData(customerPage);
        result.setSuccess(true);
        result.setTotalPage(customerPage.getTotalPages());
        result.setTotalElement(customerPage.getTotalElements());
        result.setPageNumber(customerPage.getNumber());
        result.setNumberOfElementsOnPage(customerPage.getNumberOfElements());
        result.setHasNext(customerPage.hasNext());
        result.setHasPrevious(customerPage.hasPrevious());
        result.setFirstPageOrNot(customerPage.isFirst());
        result.setLastPageOrNot(customerPage.isLast());

        return result;
    }




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
            customerVO.setCreateTime(DatetimeConverter.toString(new Date(customer.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            customerVO.setUpdateTime(DatetimeConverter.toString(new Date(customer.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(customerVO);
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
