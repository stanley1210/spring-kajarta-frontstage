package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.CarAdjust;
import com.spring_kajarta_frontstage.repository.CarAdjustRepository;
import com.spring_kajarta_frontstage.repository.CarRepository;

@Service
public class CarAdjustService {

    @Autowired
    private CarAdjustRepository carAdjustRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CarService carService;

    // 新增
    public CarAdjust create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer team_leader_id = obj.isNull("team_leader_id") ? null : obj.getInt("team_leader_id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            Integer car_id = obj.isNull("car_id") ? null : obj.getInt("car_id");
            Integer approval_status = obj.isNull("approval_status") ? null : obj.getInt("approval_status");
            Integer approval_type = obj.isNull("approval_type") ? null : obj.getInt("approval_type");
            BigDecimal floating_amount = obj.isNull("floating_amount") ? null : obj.getBigDecimal("floating_amount");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
            String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

            Optional<CarAdjust> optional = carAdjustRepo.findById(id);
            if (optional.isEmpty()) {
                CarAdjust insert = new CarAdjust();
                insert.setId(id);
                insert.setTeamLeaderId(team_leader_id);
                insert.setEmployee(null);
                insert.setCar(null);
                insert.setApprovalStatus(null);
                insert.setApprovalType(null);
                insert.setFloatingAmount(floating_amount);
                insert.setCreateTime(null);
                insert.setUpdateTime(null);

                return carAdjustRepo.save(insert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改
    public CarAdjust modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer team_leader_id = obj.isNull("team_leader_id") ? null : obj.getInt("team_leader_id");
            Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
            Integer car_id = obj.isNull("car_id") ? null : obj.getInt("car_id");
            Integer approval_status = obj.isNull("approval_status") ? null : obj.getInt("approval_status");
            Integer approval_type = obj.isNull("approval_type") ? null : obj.getInt("approval_type");
            BigDecimal floating_amount = obj.isNull("floating_amount") ? null : obj.getBigDecimal("floating_amount");
            String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
            String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

            Optional<CarAdjust> optional = carAdjustRepo.findById(id);
            if (optional.isPresent()) {
                CarAdjust update = optional.get();
                update.setId(id);
                update.setTeamLeaderId(team_leader_id);
                update.setEmployee(null);
                update.setCar(null);
                update.setApprovalStatus(null);
                update.setApprovalType(null);
                update.setFloatingAmount(floating_amount);
                update.setCreateTime(null);
                update.setUpdateTime(null);

                return carAdjustRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查全部
    public List<CarAdjust> select(CarAdjust carAdjustbean) {
        List<CarAdjust> result = null;
        if (carAdjustbean != null && carAdjustbean.getId() != null) {
            Optional<CarAdjust> optional = carAdjustRepo.findById(carAdjustbean.getId());
            if (optional.isPresent()) {
                result = new ArrayList<>();
                result.add(optional.get());
            }
        } else {
            result = carAdjustRepo.findAll();
        }
        return result;
    }

    // 查詢一筆
    public CarAdjust findById(Integer id) {
        if (id != null) {
            Optional<CarAdjust> optional = carAdjustRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 查詢多筆
}
