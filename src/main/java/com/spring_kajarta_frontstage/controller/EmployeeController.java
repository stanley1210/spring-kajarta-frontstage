package com.spring_kajarta_frontstage.controller;


import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.CustomerVO;
import com.kajarta.demo.vo.EmployeeVO;
import com.spring_kajarta_frontstage.service.EmployeeService;
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

@Tag(name = "管理後台-員工")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "員工資訊-依據員工id查詢單筆")
    @GetMapping("/info")
    public Result<EmployeeVO> info(@Parameter(description = "員工id") Integer employeeId) {
        // 依據token獲取後台登入員工

        log.info("{}-後台查詢員工資訊-單筆：{}", "到時候換成上一步拿到的管理員", employeeId);
        EmployeeVO employeeVO;
        try {
            Employee employee = employeeService.findById(employeeId);
            employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVO);
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return ResultUtil.success(employeeVO);


    }

}
