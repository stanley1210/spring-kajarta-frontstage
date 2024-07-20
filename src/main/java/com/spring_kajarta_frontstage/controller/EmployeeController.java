package com.spring_kajarta_frontstage.controller;


import com.kajarta.demo.domian.Result;
import com.kajarta.demo.domian.ResultNew;
import com.kajarta.demo.enums.AccountTypeEnum;
import com.kajarta.demo.enums.BranchEnum;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.EmployeeVO;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "管理後台-員工")
@CrossOrigin
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // 統計員工數量
    @Operation(summary = "統計員工數量")
    @GetMapping("/count")
    public Result<Long> countEmployees() {
        long count = employeeService.countEmployees();
        return ResultUtil.success(count);
    }

    @Operation(summary = "員工資訊-查詢全部")
    @GetMapping("/all")
    public Result<List<EmployeeVO>> findAll() {
        // todo:依據token獲取後台登入用戶

        log.info("{}-後台查詢員工資訊-全部", "到時候換成上一步拿到的管理員");
        List<EmployeeVO> employeeVOList;
        try {
            employeeVOList = employeeService.findAll();
            log.info("查詢到 {} 條員工數據", employeeVOList.size());
        } catch (Exception e) {
            log.error("查詢出錯", e);
            return ResultUtil.error("查詢出錯");
        }
        return ResultUtil.success(employeeVOList);
    }

    @Operation(summary = "員工資訊-依據員工id查詢單筆")
    @GetMapping("/info/{employeeId}")
    public Result<EmployeeVO> info(@Parameter(description = "員工id") @PathVariable Integer employeeId) {
        // 依據token獲取後台登入員工

        log.info("{}-後台查詢員工資訊-單筆：{}", "到時候換成上一步拿到的管理員", employeeId);
        EmployeeVO employeeVO;
        try {
            Employee employee = employeeService.findById(employeeId);
            employeeVO = new EmployeeVO();

            BeanUtils.copyProperties(employee, employeeVO);
            employeeVO.setTeamLeaderId(employee.getTeamLeader().getId());
            employeeVO.setTeamLeaderName(employee.getTeamLeader().getName());
            employeeVO.setAccountTypeName(AccountTypeEnum.getByCode(employee.getAccountType()).getAccountType());
            employeeVO.setBranchCity(BranchEnum.getByCode(employee.getBranch()).getCity());
            employeeVO.setBranchAddress(BranchEnum.getByCode(employee.getBranch()).getAddress());
            employeeVO.setBranchName(BranchEnum.getByCode(employee.getBranch()).getBranchName());
            employeeVO.setCreateTime(DatetimeConverter.toString(new Date(employee.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            employeeVO.setUpdateTime(DatetimeConverter.toString(new Date(employee.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }
        return ResultUtil.success(employeeVO);
    }

    @Operation(summary = "員工資訊-依多條件查詢(分頁)")
    @PostMapping("/query")
    public ResultNew<Page<EmployeeVO>> queryEmployees(@RequestBody EmployeeVO employeeVO, HttpServletRequest request) {
        // 解析token todo

        log.info("後台查詢員工資訊-多條件查詢(分頁)");

        Page<EmployeeVO> employeePage = employeeService.findByConditionsWithPagination(employeeVO);

        ResultNew<Page<EmployeeVO>> result = new ResultNew<>();
        result.setCode(200);
        result.setMsg("查詢成功");
        result.setData(employeePage);
        result.setSuccess(true);
        return result;
    }

    @Operation(summary = "員工資訊-新增員工")
    @PostMapping(value = "/add")
    public Result<EmployeeVO> addEmployee(@RequestBody EmployeeVO employeeVO) {
        // todo:依據token獲取後台登入用戶


        log.info("{}-新增客戶資訊：{}", "到時候換成上一步拿到的管理員", employeeVO.toString());
        try {
            employeeVO = employeeService.create(employeeVO);
        } catch (Exception e) {
            log.error("Error while creating employee", e);
            return ResultUtil.error("新增員工出錯");
        }
        return ResultUtil.success(employeeVO);
    }

    @Operation(summary = "員工資訊-修改員工")
    @PutMapping(value = "/modify/{employeeId}")
    public Result<EmployeeVO> modifyEmployee(
            @Parameter(description = "員工id") @PathVariable Integer employeeId,
            @RequestBody EmployeeVO employeeVO) {
        // todo:依據token獲取後台登入用戶

        log.info("{}-修改員工資訊：{}", "到時候換成上一步拿到的管理員", employeeVO.toString());
        employeeVO.setId(employeeId);  // 確保傳入的客戶資料有正確的ID
        try {
            EmployeeVO updateEmployee = employeeService.modify(employeeVO);
            if (updateEmployee == null) {
                return ResultUtil.error("找不到員工ID: " + employeeId);
            }
            return ResultUtil.success(updateEmployee);

        } catch (Exception e){
            return ResultUtil.error("修改員工出錯");
        }
    }

}
