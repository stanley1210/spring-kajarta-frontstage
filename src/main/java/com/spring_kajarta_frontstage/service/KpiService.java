package com.spring_kajarta_frontstage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Kpi;
import com.spring_kajarta_frontstage.repository.KpiRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class KpiService {
    @Autowired
    private KpiRepository kpiRepo;

    @Autowired
    private EmployeeService employeeService;

    // 新增
    public Kpi create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            String season_str_day = obj.isNull("season_str_day") ? null : obj.getString("season_str_day");
            Integer team_leader_rating = obj.isNull("team_leader_rating") ? null : obj.getInt("team_leader_rating");
            Integer sales_score = obj.isNull("sales_score") ? null : obj.getInt("sales_score");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
            String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

            Optional<Kpi> optional = kpiRepo.findById(id);
            if (optional.isEmpty()) {
                Kpi insert = new Kpi();
                insert.setId(id);
                insert.setSeasonStrDay(null);
                insert.setTeamLeaderRating(team_leader_rating);
                insert.setSalesScore(sales_score);
                insert.setEmployee(null); // -----------
                insert.setCreateTime(null);
                insert.setUpdateTime(null);

                return kpiRepo.save(insert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public Kpi modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            String season_str_day = obj.isNull("season_str_day") ? null : obj.getString("season_str_day");
            Integer team_leader_rating = obj.isNull("team_leader_rating") ? null : obj.getInt("team_leader_rating");
            Integer sales_score = obj.isNull("sales_score") ? null : obj.getInt("sales_score");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
            String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

            Optional<Kpi> optional = kpiRepo.findById(id);
            if (optional.isPresent()) {
                Kpi update = optional.get();
                update.setId(id);
                update.setSeasonStrDay(null);
                update.setTeamLeaderRating(team_leader_rating);
                update.setSalesScore(sales_score);
                update.setEmployee(null);
                update.setCreateTime(null);
                update.setUpdateTime(null);

                return kpiRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<Kpi> select(Kpi kpibean) {
        List<Kpi> result = null;
        if (kpibean != null && kpibean.getId() != null) {
            Optional<Kpi> optional = kpiRepo.findById(kpibean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = kpiRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public Kpi findById(Integer id) {
        if (id != null) {
            Optional<Kpi> optional = kpiRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 判斷id是否存在
    public boolean exists(Integer id) {
        if (id != null) {
            return kpiRepo.existsById(id);
        }
        return false;
    }

    // 查詢多筆

}
