package com.spring_kajarta_frontstage.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Agenda;
import com.spring_kajarta_frontstage.service.AgendaService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
        Integer business_purpose = obj.isNull("business_purpose") ? null : obj.getInt("business_purpose");
        Integer unavailable_time_str = obj.isNull("unavailable_time_str") ? null : obj.getInt("unavailable_time_str");
        Integer unavailable_time_end = obj.isNull("unavailable_time_end") ? null : obj.getInt("unavailable_time_end");
        Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");

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
    @GetMapping("/agenda/{pk}")
    public String findById(@PathVariable(name = "pk") Integer id) {
        System.out.println("AgendaController");
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();

        Agenda agenda = agendaService.findById(id);
        if (agenda != null) {
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

}
