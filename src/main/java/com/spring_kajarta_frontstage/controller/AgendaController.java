package com.spring_kajarta_frontstage.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.domian.Result;
import com.kajarta.demo.model.Agenda;
import com.kajarta.demo.utils.ResultUtil;
import com.kajarta.demo.vo.AgendaVO;
import com.kajarta.demo.vo.CarAdjustVO;
import com.spring_kajarta_frontstage.service.AgendaService;
import com.spring_kajarta_frontstage.service.EmployeeService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "管理後台-調整簽核列")
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@CrossOrigin
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private EmployeeService employeeService;

    // 新增一筆
    @Operation(summary = "時間排程列表-新增一筆 / 檢查Employee 的UavailableTime 是否有其他安排")
    @PostMapping("/agenda")
    public String create(@Parameter(description = "新增排程資料") @RequestBody String body) {
        JSONObject responseBody = new JSONObject();

        JSONObject obj = new JSONObject(body);
        Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
        String unavailableTimeStr = obj.isNull("unavailableTimeStr") ? null : obj.getString("unavailableTimeStr");
        String unavailableTimeEnd = obj.isNull("unavailableTimeEnd") ? null : obj.getString("unavailableTimeEnd");

        // 檢查Employee 的UavailableTime 是否有安排
        String checkEmpUavailableTime = "{\"employeeId\":" + employeeId + ","
                + "\"ckeckavailableTimeStr\":\"" + unavailableTimeStr + "\","
                + "\"ckeckavailableTimeEnd\":\"" + unavailableTimeEnd + "\"}";
        System.out.println(checkEmpUavailableTime);
        Page<Agenda> pageAgendas = agendaService.findByHQL(checkEmpUavailableTime);
        System.out.println(pageAgendas.getTotalElements());

        if (pageAgendas.getTotalElements() != 0) {
            responseBody.put("success", false);
            // 回傳重複時段資訊
            JSONObject message = new JSONObject();
            message.put("Employee", employeeService.findById(employeeId).getName());
            message.put("EmployeeID", employeeId);
            message.put("unavailableTimeStr", unavailableTimeStr);
            message.put("unavailableTimeEnd", unavailableTimeEnd);
            message.put("errorMSG", "時段內有排程");

            responseBody.put("message", message);
        } else {
            Agenda agenda = agendaService.create(body);
            if (agenda == null) {
                responseBody.put("success", false);
                responseBody.put("message", "排程新增失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "排程新增成功");
            }
        }
        return responseBody.toString();
    }

    // 修改一筆
    @Operation(summary = "時間排程列表-修改一筆 / 檢查ID / 檢查Employee 的UavailableTime 是否有其他安排 / 檢查(setUnavailableStatus != 3)是否為自行排程")
    @PutMapping("/agenda/{id}")
    public Result<AgendaVO> modify(@Parameter(description = "新增排程ID") @PathVariable Integer id,
            @Parameter(description = "修改排程資料") @RequestBody String body) {

        if (id == null) {
            return ResultUtil.error("排程Id是必要欄位");
        } else {
            if (!agendaService.exists(id)) {
                return ResultUtil.error("排程Id不存在");
            } else {
                JSONObject obj = new JSONObject(body);
                Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
                String unavailableTimeStr = obj.isNull("unavailableTimeStr") ? null
                        : obj.getString("unavailableTimeStr");
                String unavailableTimeEnd = obj.isNull("unavailableTimeEnd") ? null
                        : obj.getString("unavailableTimeEnd");
                // Integer unavailableStatus =
                // agendaService.findById(id).getUnavailableStatus();

                // 檢查Employee 的UavailableTime 是否有安排
                String checkEmpUavailableTime = "{\"employeeId\":" + employeeId + ","
                        + "\"ckeckavailableTimeStr\":\"" + unavailableTimeStr + "\","
                        + "\"ckeckavailableTimeEnd\":\"" + unavailableTimeEnd + "\","
                        + "\"exceptid\":\"" + id + "\"}";
                System.out.println(checkEmpUavailableTime);
                Page<Agenda> pageAgendas = agendaService.findByHQL(checkEmpUavailableTime);
                System.out.println(pageAgendas.getTotalElements());
                // if (unavailableStatus != 3) {
                // return ResultUtil.error("請假&賞車排程無法直接修改");
                // } else {

                if (pageAgendas.getTotalElements() != 0) {

                    // 回傳重複時段資訊
                    AgendaVO message = new AgendaVO();
                    message.setEmployeeName(employeeService.findById(employeeId).getName());
                    message.setEmployeeId(employeeId);
                    message.setUnavailableTimeStr(
                            DatetimeConverter.parse(unavailableTimeStr, "yyyy-MM-dd hh:mm:ss"));
                    message.setUnavailableTimeEnd(
                            DatetimeConverter.parse(unavailableTimeEnd, "yyyy-MM-dd hh:mm:ss"));

                    Result<AgendaVO> timeError = ResultUtil.error("時段內有排程");
                    timeError.setData(message);

                    return timeError;
                } else {
                    Agenda agenda = agendaService.modify(body);
                    if (agenda == null) {
                        return ResultUtil.error("排程修改失敗");
                    } else {

                        AgendaVO agendaVO = agendaService.vOChange(agenda);

                        return ResultUtil.success(agendaVO);
                    }
                }
                // }
            }
        }
    }

    // 刪除一筆
    @DeleteMapping("/agenda/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "排程Id是必要欄位");
        } else {
            if (!agendaService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "排程Id不存在");
            } else {
                if (!agendaService.remove(id)) {
                    responseBody.put("success", false);
                    responseBody.put("message", "排程刪除失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "排程刪除成功");
                }
            }
        }
        return responseBody.toString();
    }

    // 單筆查詢
    @Operation(summary = "時間排程表-依據排程id查詢單筆")
    @GetMapping("/agenda/{pk}")
    public String findById(@Parameter(description = "排程id") @PathVariable(name = "pk") Integer id) {
        System.out.println("AgendaController");
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();

        Agenda agenda = agendaService.findById(id);
        if (agenda != null) {
            String unavailableTimeStr = DatetimeConverter.toString(agenda.getUnavailableTimeStr(), "yyyy-MM-dd");
            String unavailableTimeEnd = DatetimeConverter.toString(agenda.getUnavailableTimeEnd(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(agenda.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(agenda.getUpdateTime(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()
                    .put("id", agenda.getId())
                    .put("employeeId", agenda.getEmployee().getName())
                    .put("businessPurpose", agenda.getBusinessPurpose())
                    .put("unavailableTimeStr", unavailableTimeStr)
                    .put("unavailableTimeEnd", unavailableTimeEnd)
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("unavailableStatus", agenda.getUnavailableStatus());
            array = array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 多筆查詢
    @PostMapping("/agenda/find")
    public String find(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        List<Agenda> agendas = agendaService.find(body);
        JSONArray array = new JSONArray();

        if (agendas != null && !agendas.isEmpty()) {
            for (Agenda agenda : agendas) {

                String unavailableTimeStr = DatetimeConverter.toString(agenda.getUnavailableTimeStr(), "yyyy-MM-dd");
                String unavailableTimeEnd = DatetimeConverter.toString(agenda.getUnavailableTimeEnd(), "yyyy-MM-dd");
                String createTime = DatetimeConverter.toString(agenda.getCreateTime(), "yyyy-MM-dd");
                String updateTime = DatetimeConverter.toString(agenda.getUpdateTime(), "yyyy-MM-dd");
                JSONObject item = new JSONObject()
                        .put("id", agenda.getId())
                        .put("employeeId", agenda.getEmployee().getName())
                        .put("businessPurpose", agenda.getBusinessPurpose())
                        .put("unavailableTimeStr", unavailableTimeStr)
                        .put("unavailableTimeEnd", unavailableTimeEnd)
                        .put("createTime", createTime)
                        .put("updateTime", updateTime)
                        .put("unavailableStatus", agenda.getUnavailableStatus());
                array = array.put(item);
            }
        }

        long count = agendaService.count(body);
        responseBody.put("count", count);
        responseBody.put("list", array);

        return responseBody.toString();
    }

    // // 多件查詢 Spring版
    // @Operation(summary = "時間排程列表-依據多條件查詢，含分頁查全部")
    // @PostMapping("/agenda/find2")
    // public String find2(@Parameter(description = "排程查詢條件") @RequestBody String
    // body) {
    // // todo:依據多條件(JSON)後台登入用戶
    // // 條件 - id, employee, date_time_str, date_create_time,
    // // date_time_end, unavailable_status
    // // 分頁 - is_page, max, dir, order

    // JSONObject responseBody = new JSONObject();
    // List<Agenda> agendas = agendaService.find2(body);
    // JSONArray array = new JSONArray();

    // if (agendas != null && !agendas.isEmpty()) {
    // for (Agenda agenda : agendas) {

    // String unavailable_time_str =
    // DatetimeConverter.toString(agenda.getUnavailableTimeStr(), "yyyy-MM-dd");
    // String unavailable_time_end =
    // DatetimeConverter.toString(agenda.getUnavailableTimeEnd(), "yyyy-MM-dd");
    // String create_time = DatetimeConverter.toString(agenda.getCreateTime(),
    // "yyyy-MM-dd");
    // String update_time = DatetimeConverter.toString(agenda.getUpdateTime(),
    // "yyyy-MM-dd");
    // JSONObject item = new JSONObject()
    // .put("id", agenda.getId())
    // .put("employee_id", agenda.getEmployee().getName())
    // .put("business_purpose", agenda.getBusinessPurpose())
    // .put("unavailable_time_str", unavailable_time_str)
    // .put("unavailable_time_end", unavailable_time_end)
    // .put("create_time", create_time)
    // .put("update_time", update_time)
    // .put("unavailable_status", agenda.getUnavailableStatus());
    // array = array.put(item);
    // }
    // }

    // responseBody.put("list", array);
    // return responseBody.toString();
    // }

    // 多條件查詢 Spring版 + VO
    @Operation(summary = "時間排程列表-依據多條件查詢，含分頁查全部")
    @PostMapping("/agenda/findByHQL")
    public Result<List<AgendaVO>> findByHQL(@Parameter(description = "排程查詢條件") @RequestBody String body) {
        // todo:依據多條件(JSON)
        // 條件 - id, employee, date_time_str, date_create_time,
        // date_time_end, unavailable_status
        // 分頁 - is_page, max, dir, order

        log.info("{}-後台查詢時間排程資訊-多條件JSONE：{}", "到時候換成上一步拿到的管理員", body);
        Result<List<AgendaVO>> result = new Result<List<AgendaVO>>();
        List<AgendaVO> agendaVOs = new ArrayList<>();
        try {
            Page<Agenda> pageAgendas = agendaService.findByHQL(body);
            List<Agenda> agendas = pageAgendas.getContent();

            if (agendas != null && !agendas.isEmpty()) {
                for (Agenda agenda : agendas) {

                    AgendaVO agendaVO = agendaService.vOChange(agenda);

                    agendaVOs.add(agendaVO);
                }
            }

            result.setData(agendaVOs);
            result.setSuccess(true);
            result.setTotalPage(pageAgendas.getTotalPages());
            result.setTotalElement(pageAgendas.getTotalElements());
            result.setPageNumber(pageAgendas.getNumber());
            result.setNumberOfElementsOnPage(pageAgendas.getNumberOfElements());
            result.setHasNext(pageAgendas.hasNext());
            result.setHasPrevious(pageAgendas.hasPrevious());
            result.setFirstPageOrNot(pageAgendas.isFirst());
            result.setLastPageOrNot(pageAgendas.isLast());

        } catch (Exception e) {
            return ResultUtil.error("查詢出錯");
        }

        return result;
    }

}
