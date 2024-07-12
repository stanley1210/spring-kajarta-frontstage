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

    // 新增一筆
    @PostMapping("/agenda")
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();

        JSONObject obj = new JSONObject(body);
        // Integer id = obj.isNull("id") ? null : obj.getInt("id");
        Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
        Integer businessPurpose = obj.isNull("businessPurpose") ? null : obj.getInt("businessPurpose");
        Integer unavailableTimeStr = obj.isNull("unavailableTimeStr") ? null : obj.getInt("unavailableTimeStr");
        Integer unavailableTimeEnd = obj.isNull("unavailableTimeEnd") ? null : obj.getInt("unavailableTimeEnd");
        Integer unavailableStatus = obj.isNull("unavailableStatus") ? null : obj.getInt("unavailableStatus");

        if (false) {// agendaService.判斷時間是否重複(id)
            responseBody.put("success", false);
            responseBody.put("message", "XX已存在");
        } else {
            Agenda agenda = agendaService.create(body);
            if (agenda == null) {
                responseBody.put("success", false);
                responseBody.put("message", "XX新增失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "XX新增成功");
            }
        }
        return responseBody.toString();
    }

    // 修改一筆
    @PutMapping("/agenda/{id}")
    public String modify(@PathVariable Integer id, @RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "排程Id是必要欄位");
        } else {
            if (!agendaService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "排程Id不存在");
            } else {
                Agenda agenda = agendaService.modify(body);
                if (agenda == null) {
                    responseBody.put("success", false);
                    responseBody.put("message", "排程修改失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "排程修改成功");
                }
            }
        }
        return responseBody.toString();
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

                String unavailable_time_str = DatetimeConverter.toString(agenda.getUnavailableTimeStr(), "yyyy-MM-dd");
                String unavailable_time_end = DatetimeConverter.toString(agenda.getUnavailableTimeEnd(), "yyyy-MM-dd");
                String create_time = DatetimeConverter.toString(agenda.getCreateTime(), "yyyy-MM-dd");
                String update_time = DatetimeConverter.toString(agenda.getUpdateTime(), "yyyy-MM-dd");
                JSONObject item = new JSONObject()
                        .put("id", agenda.getId())
                        .put("employee_id", agenda.getEmployee().getName())
                        .put("business_purpose", agenda.getBusinessPurpose())
                        .put("unavailable_time_str", unavailable_time_str)
                        .put("unavailable_time_end", unavailable_time_end)
                        .put("create_time", create_time)
                        .put("update_time", update_time)
                        .put("unavailable_status", agenda.getUnavailableStatus());
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

    // 多件查詢 Spring版 + VO
    @Operation(summary = "時間排程列表-依據多條件查詢，含分頁查全部")
    @PostMapping("/agenda/find2")
    public Result<List<AgendaVO>> find2(@Parameter(description = "排程查詢條件") @RequestBody String body) {
        // todo:依據多條件(JSON)後台登入用戶
        // 條件 - id, employee, date_time_str, date_create_time,
        // date_time_end, unavailable_status
        // 分頁 - is_page, max, dir, order

        log.info("{}-後台查詢時間排程資訊-多條件JSONE：{}", "到時候換成上一步拿到的管理員", body);
        Result<List<AgendaVO>> result = new Result<List<AgendaVO>>();
        List<AgendaVO> agendaVOs = new ArrayList<>();
        try {
            Page<Agenda> pageAgendas = agendaService.find2(body);
            List<Agenda> agendas = pageAgendas.getContent();

            if (agendas != null && !agendas.isEmpty()) {
                for (Agenda agenda : agendas) {

                    AgendaVO agendaVO = new AgendaVO();
                    BeanUtils.copyProperties(agenda, agendaVO);

                    // 排程分類 UnavailableStatu
                    switch (agenda.getUnavailableStatus()) {
                        case 1:
                            agendaVO.setUnavailableStatusName("請假");
                            break;
                        case 2:
                            agendaVO.setUnavailableStatusName("賞車");
                            break;
                        case 3:
                            agendaVO.setUnavailableStatusName("公事安排");
                            break;

                        default:
                            agendaVO.setUnavailableStatusName(
                                    "排程分類錯誤 UnavailableStatus = " + agenda.getUnavailableStatus().toString());
                    }
                    agendaVO.setEmployeeName(agenda.getEmployee().getName());

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
