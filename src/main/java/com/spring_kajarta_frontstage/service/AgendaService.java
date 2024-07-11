package com.spring_kajarta_frontstage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Agenda;
import com.kajarta.demo.model.Employee;
import com.spring_kajarta_frontstage.repository.AgendaRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepo;

    @Autowired
    private EmployeeService employeeService;

    // 刪除
    public boolean remove(Integer id) {
        if (id != null) {
            Optional<Agenda> optional = agendaRepo.findById(id);
            if (optional.isPresent()) {
                agendaRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

    // 新增
    public Agenda create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            String business_purpose = obj.isNull("business_purpose") ? null : obj.getString("business_purpose");
            String unavailable_time_str = obj.isNull("unavailable_time_str") ? null
                    : obj.getString("unavailable_time_str");
            String unavailable_time_end = obj.isNull("unavailable_time_end") ? null
                    : obj.getString("unavailable_time_end");
            Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");

            Optional<Agenda> optional = agendaRepo.findById(id);
            if (optional.isEmpty()) {
                Agenda insert = new Agenda();
                insert.setId(id);
                insert.setEmployee(employeeService.findById(employee_id));
                insert.setBusinessPurpose(business_purpose);
                insert.setUnavailableTimeStr(DatetimeConverter.parse(unavailable_time_str, "yyyy-MM-dd hh:mm:ss"));
                insert.setUnavailableTimeEnd(DatetimeConverter.parse(unavailable_time_end, "yyyy-MM-dd hh:mm:ss"));
                insert.setUnavailableStatus(unavailable_status);

                return agendaRepo.save(insert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public Agenda modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            String business_purpose = obj.isNull("business_purpose") ? null : obj.getString("business_purpose");
            String unavailable_time_str = obj.isNull("unavailable_time_str") ? null
                    : obj.getString("unavailable_time_str");
            String unavailable_time_end = obj.isNull("unavailable_time_end") ? null
                    : obj.getString("unavailable_time_end");
            Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");

            Optional<Agenda> optional = agendaRepo.findById(id);
            if (optional.isPresent()) {
                Agenda update = new Agenda();
                update.setId(id);
                update.setEmployee(employeeService.findById(employee_id));
                update.setBusinessPurpose(business_purpose);
                update.setUnavailableTimeStr(DatetimeConverter.parse(unavailable_time_str, "yyyy-MM-dd hh:mm:ss"));
                update.setUnavailableTimeEnd(DatetimeConverter.parse(unavailable_time_end, "yyyy-MM-dd hh:mm:ss"));
                update.setUnavailableStatus(unavailable_status);

                return agendaRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<Agenda> select(Agenda agendabean) {
        List<Agenda> result = null;
        if (agendabean != null && agendabean.getId() != null) {
            Optional<Agenda> optional = agendaRepo.findById(agendabean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = agendaRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public Agenda findById(Integer id) {
        System.out.println("AgendaService");
        if (id != null) {
            Optional<Agenda> optional = agendaRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 判斷id是否存在
    public boolean exists(Integer id) {
        if (id != null) {
            return agendaRepo.existsById(id);
        }
        return false;
    }

    // 計算數量
    public long count(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return agendaRepo.count(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 查詢多筆
    public List<Agenda> find(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return agendaRepo.find(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查詢多筆 @Query 測試
    public List<Agenda> find2(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            String unavailable_time_str = obj.isNull("unavailable_time_str") ? null
                    : obj.getString("unavailable_time_str");
            String unavailable_time_end = obj.isNull("unavailable_time_end") ? null
                    : obj.getString("unavailable_time_end");
            Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");

            java.util.Date date_time_str = DatetimeConverter.parse(unavailable_time_str,
                    "yyyy-MM-dd");
            java.util.Date date_time_end = DatetimeConverter.parse(unavailable_time_end,
                    "yyyy-MM-dd");
            java.util.Date date_create_time = DatetimeConverter.parse(create_time,
                    "yyyy-MM-dd");
            Employee employee = employeeService.findById(employee_id);

            return agendaRepo.find2(id, employee, date_time_str, date_time_end,
                    date_create_time,
                    unavailable_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
