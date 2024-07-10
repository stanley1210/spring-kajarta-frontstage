package com.spring_kajarta_frontstage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.ViewCarAssigned;
import com.spring_kajarta_frontstage.repository.ViewCarAssignedRepository;

@Service
public class ViewCarAssignedService {

    @Autowired
    private ViewCarAssignedRepository viewCarAssignedRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ViewCarService viewCarService;

    // 新增
    public ViewCarAssigned create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer team_leader_id = obj.isNull("team_leader_id") ? null : obj.getInt("team_leader_id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            Integer view_car_id = obj.isNull("view_car_id") ? null : obj.getInt("view_car_id");
            Integer assigned_status = obj.isNull("assigned_status") ? null : obj.getInt("assigned_status");

            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(id);
            if (optional.isEmpty()) {
                ViewCarAssigned insert = new ViewCarAssigned();
                insert.setId(id);
                insert.setTeamLeaderId(team_leader_id);
                ;
                insert.setEmployee(employeeService.findById(employee_id));
                insert.setViewCar(viewCarService.findById(view_car_id));
                insert.setAssignedStatus(assigned_status);

                return viewCarAssignedRepo.save(insert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public ViewCarAssigned modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer team_leader_id = obj.isNull("team_leader_id") ? null : obj.getInt("team_leader_id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            Integer view_car_id = obj.isNull("view_car_id") ? null : obj.getInt("view_car_id");
            Integer assigned_status = obj.isNull("assigned_status") ? null : obj.getInt("assigned_status");

            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(id);
            if (optional.isPresent()) {
                ViewCarAssigned update = optional.get();
                update.setId(id);
                update.setTeamLeaderId(team_leader_id);
                update.setEmployee(viewCarService.findById(view_car_id));
                update.setViewCar(null);
                update.setAssignedStatus(assigned_status);

                return viewCarAssignedRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<ViewCarAssigned> select(ViewCarAssigned viewCarAssignedbean) {
        List<ViewCarAssigned> result = null;
        if (viewCarAssignedbean != null && viewCarAssignedbean.getId() != null) {
            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(viewCarAssignedbean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = viewCarAssignedRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public ViewCarAssigned findById(Integer id) {
        if (id != null) {
            Optional<ViewCarAssigned> optional = viewCarAssignedRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 判斷id是否存在
    public boolean exists(Integer id) {
        if (id != null) {
            return viewCarAssignedRepo.existsById(id);
        }
        return false;
    }

    // 查詢多筆
}
