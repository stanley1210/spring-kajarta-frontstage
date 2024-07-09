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
    private ViewCarAssignedRepository viewCarAssignedRepository;

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
            String season_str_day = obj.isNull("season_str_day") ? null : obj.getString("season_str_day");
            Integer sales_score = obj.isNull("sales_score") ? null : obj.getInt("sales_score");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
            String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

            Optional<ViewCarAssigned> optional = viewCarAssignedRepository.findById(id);
            if (optional.isEmpty()) {
                ViewCarAssigned insert = new ViewCarAssigned();
                insert.setId(id);
                insert.setTeamLeaderId(team_leader_id);
                ;
                insert.setEmployee(null);
                insert.set(sales_score);
                insert.setEmployee(null);
                insert.setCreateTime(null);
                insert.setUpdateTime(null);

                return viewCarAssignedRepository.save(insert);
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
            String season_str_day = obj.isNull("season_str_day") ? null : obj.getString("season_str_day");
            Integer team_leader_rating = obj.isNull("team_leader_rating") ? null : obj.getInt("team_leader_rating");
            Integer sales_score = obj.isNull("sales_score") ? null : obj.getInt("sales_score");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
            String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

            Optional<ViewCarAssigned> optional = viewCarAssignedRepository.findById(id);
            if (optional.isPresent()) {
                ViewCarAssigned update = optional.get();
                update.setId(id);
                update.setSeasonStrDay(null);
                update.setTeamLeaderRating(team_leader_rating);
                update.setSalesScore(sales_score);
                update.setEmployee(null);
                update.setCreateTime(null);
                update.setUpdateTime(null);

                return viewCarAssignedRepository.save(update);
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
            Optional<ViewCarAssigned> optional = viewCarAssignedRepository.findById(viewCarAssignedbean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = viewCarAssignedRepository.findAll();
        }
        return result;
    }

    // 查詢一筆
    public ViewCarAssigned findById(Integer id) {
        if (id != null) {
            Optional<ViewCarAssigned> optional = viewCarAssignedRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 查詢多筆
}
