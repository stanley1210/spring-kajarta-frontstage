package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Preference;
import com.spring_kajarta_frontstage.repository.PreferenceRepository;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CustomerService;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepo;

    @Autowired
    private CarInfoService carinfoService;

    @Autowired
    private CustomerService customerService;

    public boolean exists(Integer id) { // 檢查ID
        return preferenceRepo.existsById(id);
    }

    public Preference findById(Integer Id) { // 查單筆
        Optional<Preference> optional = preferenceRepo.findById(Id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Preference> select() { // 查全部
        return preferenceRepo.findAll();
    }

    public List<Preference> fuzzySearch(String keyword) { // 模糊查詢
        return preferenceRepo.fuzzySearch(keyword);
    }

    public Preference create(String json) { // 新增
        try {
            JSONObject obj = new JSONObject(json);

            String selectName = obj.isNull("selectName") ? null : obj.getString("selectName");
            Integer productionYear = obj.isNull("productionYear") ? null : obj.getInt("productionYear");
            BigDecimal price = obj.isNull("price") ? null : obj.getBigDecimal("price");
            Integer milage = obj.isNull("milage") ? null : obj.getInt("milage");
            Integer score = obj.isNull("score") ? null : obj.getInt("score");
            Integer customerId = obj.isNull("customer_id") ? null : obj.getInt("customer_id");
            Integer carinfoId = obj.isNull("carinfo_id") ? null : obj.getInt("carinfo_id");
            Integer brand = obj.isNull("brand") ? null : obj.getInt("brand");
            Integer suspension = obj.isNull("suspension") ? null : obj.getInt("suspension");
            Integer door = obj.isNull("door") ? null : obj.getInt("door");
            Integer passenger = obj.isNull("passenger") ? null : obj.getInt("passenger");
            Integer rearWheel = obj.isNull("rear_wheel") ? null : obj.getInt("rear_wheel");
            Integer gasoline = obj.isNull("gasoline") ? null : obj.getInt("gasoline");
            Integer transmission = obj.isNull("transmission") ? null : obj.getInt("transmission");
            Integer cc = obj.isNull("cc") ? null : obj.getInt("cc");
            Integer hp = obj.isNull("hp") ? null : obj.getInt("hp");
            Double torque = obj.isNull("torque") ? null : obj.getDouble("torque");
            Integer preferencesLists = obj.isNull("preferences_lists") ? null : obj.getInt("preferences_lists");

            Customer customer = customerService.findById(customerId);
            Carinfo carInfo = carinfoService.findById(carinfoId);

            Preference insert = new Preference();
            insert.setSelectName(selectName);
            insert.setProductionYear(productionYear);
            insert.setPrice(price);
            insert.setMilage(milage);
            insert.setScore(score);
            insert.setCustomer(customer);
            insert.setCarinfo(carInfo);
            insert.setBrand(brand);
            insert.setSuspension(suspension);
            insert.setDoor(door);
            insert.setPassenger(passenger);
            insert.setRearWheel(rearWheel);
            insert.setGasoline(gasoline);
            insert.setTransmission(transmission);
            insert.setCc(cc);
            insert.setHp(hp);
            insert.setTorque(torque);
            insert.setPreferencesLists(preferencesLists);

            return preferenceRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Preference modify(String json) { // 修改
        try {
            JSONObject obj = new JSONObject(json);

            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            String selectName = obj.isNull("selectName") ? null : obj.getString("selectName");
            Integer productionYear = obj.isNull("productionYear") ? null : obj.getInt("productionYear");
            BigDecimal price = obj.isNull("price") ? null : obj.getBigDecimal("price");
            Integer milage = obj.isNull("milage") ? null : obj.getInt("milage");
            Integer score = obj.isNull("score") ? null : obj.getInt("score");
            Integer customerId = obj.isNull("customer_id") ? null : obj.getInt("customer_id");
            Integer carinfoId = obj.isNull("carinfo_id") ? null : obj.getInt("carinfo_id");
            Integer brand = obj.isNull("brand") ? null : obj.getInt("brand");
            Integer suspension = obj.isNull("suspension") ? null : obj.getInt("suspension");
            Integer door = obj.isNull("door") ? null : obj.getInt("door");
            Integer passenger = obj.isNull("passenger") ? null : obj.getInt("passenger");
            Integer rearWheel = obj.isNull("rear_wheel") ? null : obj.getInt("rear_wheel");
            Integer gasoline = obj.isNull("gasoline") ? null : obj.getInt("gasoline");
            Integer transmission = obj.isNull("transmission") ? null : obj.getInt("transmission");
            Integer cc = obj.isNull("cc") ? null : obj.getInt("cc");
            Integer hp = obj.isNull("hp") ? null : obj.getInt("hp");
            Double torque = obj.isNull("torque") ? null : obj.getDouble("torque");
            Integer preferencesLists = obj.isNull("preferences_lists") ? null : obj.getInt("preferences_lists");

            Customer customer = customerService.findById(customerId);
            Carinfo carInfo = carinfoService.findById(carinfoId);
            Optional<Preference> optional = preferenceRepo.findById(id);
            if (optional.isPresent()) {
                Preference update = optional.get();
                update.setSelectName(selectName);
                update.setProductionYear(productionYear);
                update.setPrice(price);
                update.setMilage(milage);
                update.setScore(score);
                update.setCustomer(customer);
                update.setCarinfo(carInfo);
                update.setBrand(brand);
                update.setSuspension(suspension);
                update.setDoor(door);
                update.setPassenger(passenger);
                update.setRearWheel(rearWheel);
                update.setGasoline(gasoline);
                update.setTransmission(transmission);
                update.setCc(cc);
                update.setHp(hp);
                update.setTorque(torque);
                update.setPreferencesLists(preferencesLists);

                return preferenceRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean remove(Integer id) { // 刪除
        if (id != null && preferenceRepo.existsById(id)) {
            preferenceRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
