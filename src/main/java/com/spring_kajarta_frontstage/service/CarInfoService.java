package com.spring_kajarta_frontstage.service;

import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kajarta.demo.model.Carinfo;
import com.spring_kajarta_frontstage.repository.CarInfoRepository;

@Service
public class CarInfoService {
    @Autowired
    private CarInfoRepository carinfoRepo;

    public List<Carinfo> select() { // 查全部
        return carinfoRepo.findAll();
    }

    public Carinfo findById(Integer id) { // 查單筆
        Optional<Carinfo> optional = carinfoRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Carinfo> fuzzySearch(String keyword) { // 模糊查詢
        return carinfoRepo.fuzzySearch(keyword);
    }

    public Carinfo create(String json) { // 新增
        try {
            JSONObject obj = new JSONObject(json);

            Integer brand = obj.isNull("brand") ? null : obj.getInt("brand");
            String modelName = obj.isNull("modelname") ? null : obj.getString("modelname");
            Integer suspension = obj.isNull("suspension") ? null : obj.getInt("suspension");
            Integer door = obj.isNull("door") ? null : obj.getInt("door");
            Integer passenger = obj.isNull("passenger") ? null : obj.getInt("passenger");
            Integer rearWheel = obj.isNull("rearwheel") ? null : obj.getInt("rearwheel");
            Integer gasoline = obj.isNull("gasoline") ? null : obj.getInt("gasoline");
            Integer transmission = obj.isNull("transmission") ? null : obj.getInt("transmission");
            Integer cc = obj.isNull("cc") ? null : obj.getInt("cc");
            Integer hp = obj.isNull("hp") ? null : obj.getInt("hp");
            Double torque = obj.isNull("torque") ? null : obj.getDouble("torque");

            Carinfo insert = new Carinfo();
            insert.setBrand(brand);
            insert.setModelName(modelName);
            insert.setSuspension(suspension);
            insert.setDoor(door);
            insert.setPassenger(passenger);
            insert.setRearWheel(rearWheel);
            insert.setGasoline(gasoline);
            insert.setTransmission(transmission);
            insert.setCc(cc);
            insert.setHp(hp);
            insert.setTorque(torque);

            return carinfoRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Carinfo modify(String json) { // 修改
        try {
            JSONObject obj = new JSONObject(json);

            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer brand = obj.isNull("brand") ? null : obj.getInt("brand");
            String modelName = obj.isNull("modelname") ? null : obj.getString("modelname");
            Integer suspension = obj.isNull("suspension") ? null : obj.getInt("suspension");
            Integer door = obj.isNull("door") ? null : obj.getInt("door");
            Integer passenger = obj.isNull("passenger") ? null : obj.getInt("passenger");
            Integer rearWheel = obj.isNull("rearwheel") ? null : obj.getInt("rearwheel");
            Integer gasoline = obj.isNull("gasoline") ? null : obj.getInt("gasoline");
            Integer transmission = obj.isNull("transmission") ? null : obj.getInt("transmission");
            Integer cc = obj.isNull("cc") ? null : obj.getInt("cc");
            Integer hp = obj.isNull("hp") ? null : obj.getInt("hp");
            Double torque = obj.isNull("torque") ? null : obj.getDouble("torque");

            Optional<Carinfo> optional = carinfoRepo.findById(id);
            if (optional.isPresent()) {
                Carinfo update = optional.get();
                update.setBrand(brand);
                update.setModelName(modelName);
                update.setSuspension(suspension);
                update.setDoor(door);
                update.setPassenger(passenger);
                update.setRearWheel(rearWheel);
                update.setGasoline(gasoline);
                update.setTransmission(transmission);
                update.setCc(cc);
                update.setHp(hp);
                update.setTorque(torque);

                return carinfoRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(Integer id) { // 檢查ID
        return carinfoRepo.existsById(id);
    }

    public boolean remove(Integer id) { // 刪除
        if (id != null && carinfoRepo.existsById(id)) {
            carinfoRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
