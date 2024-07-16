package com.spring_kajarta_frontstage.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Preference;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CustomerService;
import com.spring_kajarta_frontstage.service.PreferenceService;

@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarInfoService carinfoService;

    @GetMapping("/find/{Id}") // 查單筆
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Preference preference = preferenceService.findById(Id);
        Customer customer = customerService.findById(Id);
        Carinfo carInfo = carinfoService.findById(Id);
        if (preference != null) {
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectName", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", customer.getId())
                    .put("carinfo_id", carInfo.getId())
                    .put("gasoline", preference.getGasoline())
                    .put("brand", preference.getBrand())
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());

            array = array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/list") // 查全部
    @ResponseBody
    public String showAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Preference> preferences = preferenceService.select();
        for (Preference preference : preferences) {
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectName", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("gasoline", preference.getGasoline())
                    .put("brand", preference.getBrand())
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search") // 模糊查詢
    @ResponseBody
    public String searchByKeyword(@RequestParam(name = "keyword") String keyword) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Preference> preferences = preferenceService.fuzzySearch(keyword);

        for (Preference preference : preferences) {
            Integer carinfoId = (preference.getCarinfo()) == null ? -1 : preference.getCarinfo().getId();
            JSONObject item = new JSONObject()
                    .put("id", preference.getId())
                    .put("selectame", preference.getSelectName())
                    .put("productionYear", preference.getProductionYear())
                    .put("price", preference.getPrice())
                    .put("milage", preference.getMilage())
                    .put("score", preference.getScore())
                    .put("customer_id", preference.getCustomer().getId())
                    .put("carinfo_id", carinfoId)
                    .put("brand", preference.getBrand())
                    .put("suspension", preference.getSuspension())
                    .put("door", preference.getDoor())
                    .put("passenger", preference.getPassenger())
                    .put("rearWheel", preference.getRearWheel())
                    .put("gasoline", preference.getGasoline())
                    .put("transmission", preference.getTransmission())
                    .put("cc", preference.getCc())
                    .put("hp", preference.getHp())
                    .put("torque", preference.getTorque())
                    .put("createTime", preference.getCreateTime())
                    .put("updateTime", preference.getUpdateTime())
                    .put("preferencesLists", preference.getPreferencesLists());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @PostMapping("/create") // 新增
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        Preference preference = preferenceService.create(body);
        if (preference == null) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗");
        } else {
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        }
        return responseBody.toString();
    }

    @PutMapping("/modify/{id}") // 修改
    public String modify(@RequestBody String body, @PathVariable(name = "id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        if (!preferenceService.exists(Id)) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不存在");
        } else {
            Preference preference = preferenceService.modify(body);
            if (preference == null) {
                responseBody.put("success", false);
                responseBody.put("message", "修改失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "修改成功");
            }

        }

        return responseBody.toString();
    }

    @DeleteMapping("/delete/{id}") // 刪除
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();

        if (!preferenceService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id不存在");
        } else {
            if (!preferenceService.remove(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "刪除失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "刪除成功");
            }
        }

        return responseBody.toString();
    }
}
