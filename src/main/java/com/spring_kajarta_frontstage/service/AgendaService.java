package com.spring_kajarta_frontstage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Agenda;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.vo.AgendaVO;
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
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            String businessPurpose = obj.isNull("businessPurpose") ? null : obj.getString("businessPurpose");
            String unavailableTimeStr = obj.isNull("unavailableTimeStr") ? null
                    : obj.getString("unavailableTimeStr");
            String unavailableTimeEnd = obj.isNull("unavailableTimeEnd") ? null
                    : obj.getString("unavailableTimeEnd");
            Integer unavailableStatus = obj.isNull("unavailableStatus") ? null : obj.getInt("unavailableStatus");

            Agenda insert = new Agenda();
            insert.setEmployee(employeeService.findById(employeeId));
            insert.setBusinessPurpose(businessPurpose);
            insert.setUnavailableTimeStr(DatetimeConverter.parse(unavailableTimeStr, "yyyy-MM-dd HH:mm:ss"));
            insert.setUnavailableTimeEnd(DatetimeConverter.parse(unavailableTimeEnd, "yyyy-MM-dd HH:mm:ss"));
            insert.setUnavailableStatus(unavailableStatus);
            return agendaRepo.save(insert);

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
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            String businessPurpose = obj.isNull("businessPurpose") ? null : obj.getString("businessPurpose");
            String unavailableTimeStr = obj.isNull("unavailableTimeStr") ? null
                    : obj.getString("unavailableTimeStr");
            String unavailableTimeEnd = obj.isNull("unavailableTimeEnd") ? null
                    : obj.getString("unavailableTimeEnd");
            Integer unavailableStatus = obj.isNull("unavailableStatus") ? null : obj.getInt("unavailableStatus");

            Optional<Agenda> optional = agendaRepo.findById(id);
            if (optional.isPresent()) {
                Agenda update = new Agenda();
                update.setId(id);
                update.setEmployee(employeeService.findById(employeeId));
                update.setBusinessPurpose(businessPurpose);
                update.setUnavailableTimeStr(DatetimeConverter.parse(unavailableTimeStr, "yyyy-MM-dd HH:mm:ss"));
                update.setUnavailableTimeEnd(DatetimeConverter.parse(unavailableTimeEnd, "yyyy-MM-dd HH:mm:ss"));
                update.setUnavailableStatus(unavailableStatus);
                update.setCreateTime(agendaRepo.findById(id).get().getCreateTime());
                update.setCreateTime(agendaRepo.findById(id).get().getUpdateTime());

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
    public Page<Agenda> findByHQL(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Employee employee = obj.isNull("employeeId") ? null : employeeService.findById(obj.getInt("employeeId"));
            Date unavailableTimeStr = obj.isNull("unavailableTimeStr") ? null
                    : DatetimeConverter.parse(obj.getString("unavailableTimeStr"), "yyyy-MM-dd HH:mm:ss");
            Date unavailableTimeEnd = obj.isNull("unavailableTimeEnd") ? null
                    : DatetimeConverter.parse(obj.getString("unavailableTimeEnd"), "yyyy-MM-dd HH:mm:ss");
            Integer unavailableStatus = obj.isNull("unavailableStatus") ? null : obj.getInt("unavailableStatus");
            Date createTime = obj.isNull("createTime") ? null
                    : DatetimeConverter.parse(obj.getString("createTime"), "yyyy-MM-dd");
            Date ckeckavailableTimeStr = obj.isNull("ckeckavailableTimeStr") ? null
                    : DatetimeConverter.parse(obj.getString("ckeckavailableTimeStr"), "yyyy-MM-dd HH:mm:ss");
            Date ckeckavailableTimeEnd = obj.isNull("ckeckavailableTimeEnd") ? null
                    : DatetimeConverter.parse(obj.getString("ckeckavailableTimeEnd"), "yyyy-MM-dd HH:mm:ss");
            Integer exceptid = obj.isNull("exceptid") ? null : obj.getInt("exceptid");

            Integer isPage = obj.isNull("isPage") ? 0 : obj.getInt("isPage");
            Integer max = obj.isNull("max") ? 4 : obj.getInt("max");
            boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir");
            String order = obj.isNull("order") ? "id" : obj.getString("order");
            Sort sort = dir ? Sort.by(Sort.Direction.ASC, order) : Sort.by(Sort.Direction.DESC, order);

            Pageable pgb = PageRequest.of(isPage.intValue(), max.intValue(), sort);
            Page<Agenda> page = agendaRepo.findByHQL(id, employee, unavailableTimeStr, createTime, unavailableTimeEnd,
                    unavailableStatus, ckeckavailableTimeStr, ckeckavailableTimeEnd, exceptid, pgb);

            return page;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Bean 轉 VO
    public AgendaVO vOChange(Agenda agenda) {
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
        agendaVO.setEmployeeId(agenda.getEmployee().getId());
        agendaVO.setUnavailableTimeEndString(
                DatetimeConverter.toString(agenda.getUnavailableTimeEnd(), "yyyy-MM-dd HH:mm:ss"));
        agendaVO.setUnavailableTimeStrString(
                DatetimeConverter.toString(agenda.getUnavailableTimeStr(), "yyyy-MM-dd HH:mm:ss"));
        agendaVO.setCreateTimeString(DatetimeConverter.toString(agenda.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        agendaVO.setUpdateTimeString(DatetimeConverter.toString(agenda.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

        return agendaVO;
    }
}
