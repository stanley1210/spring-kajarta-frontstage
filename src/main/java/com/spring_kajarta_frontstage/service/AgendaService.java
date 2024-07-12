package com.spring_kajarta_frontstage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Agenda> find2(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Employee employee = obj.isNull("employee_id") ? null : employeeService.findById(obj.getInt("employee_id"));
            Date date_time_str = obj.isNull("unavailable_time_str") ? null
                    : DatetimeConverter.parse(obj.getString("unavailable_time_str"), "yyyy-MM-dd");
            Date date_time_end = obj.isNull("unavailable_time_end") ? null
                    : DatetimeConverter.parse(obj.getString("unavailable_time_end"), "yyyy-MM-dd");
            Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");
            Date date_create_time = obj.isNull("create_time") ? null
                    : DatetimeConverter.parse(obj.getString("create_time"), "yyyy-MM-dd");

            Integer is_page = obj.isNull("is_page") ? 0 : obj.getInt("is_page");
            Integer max = obj.isNull("max") ? 4 : obj.getInt("max");
            boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir");
            String order = obj.isNull("order") ? "id" : obj.getString("order");
            Sort sort = dir ? Sort.by(Sort.Direction.ASC, order) : Sort.by(Sort.Direction.DESC, order);

            Pageable pgb = PageRequest.of(is_page.intValue(), max.intValue(), sort);
            Page<Agenda> page = agendaRepo.find2(id, employee, date_time_str, date_create_time, date_time_end,
                    unavailable_status, pgb);

            return page;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
