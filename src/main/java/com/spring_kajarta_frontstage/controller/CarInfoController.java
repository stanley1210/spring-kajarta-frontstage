package com.spring_kajarta_frontstage.controller;

import java.util.LinkedHashMap;
import java.util.List;

import com.spring_kajarta_frontstage.service.CarInfoService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/carinfo")
@CrossOrigin
public class CarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @GetMapping("/list") // 查全部
    @ResponseBody
    public String showAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Carinfo> carInfos = carInfoService.select();
        for (Carinfo carInfo : carInfos) {
            LinkedHashMap<String, Object> itemMap = new LinkedHashMap<>();
            itemMap.put("id", carInfo.getId());
            itemMap.put("brand", carInfo.getBrand());
            itemMap.put("modelName", carInfo.getModelName());
            itemMap.put("suspension", carInfo.getSuspension());
            itemMap.put("door", carInfo.getDoor());
            itemMap.put("passenger", carInfo.getPassenger());
            itemMap.put("rearWheel", carInfo.getRearWheel());
            itemMap.put("gasoline", carInfo.getGasoline());
            itemMap.put("transmission", carInfo.getTransmission());
            itemMap.put("cc", carInfo.getCc());
            itemMap.put("hp", carInfo.getHp());
            itemMap.put("torque", carInfo.getTorque());
            itemMap.put("createTime", carInfo.getCreateTime());
            itemMap.put("updateTime", carInfo.getUpdateTime());

            JSONObject item = new JSONObject(itemMap);
            array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/find/{Id}") // 查單筆
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Carinfo carInfo = carInfoService.findById(Id);
        if (carInfo != null) {
            JSONObject item = new JSONObject()
                    .put("id", carInfo.getId())
                    .put("brand", carInfo.getBrand())
                    .put("modelName", carInfo.getModelName())
                    .put("suspension", carInfo.getSuspension())
                    .put("door", carInfo.getDoor())
                    .put("passenger", carInfo.getPassenger())
                    .put("rearWheel", carInfo.getRearWheel())
                    .put("gasoline", carInfo.getGasoline())
                    .put("transmission", carInfo.getTransmission())
                    .put("cc", carInfo.getCc())
                    .put("hp", carInfo.getHp())
                    .put("torque", carInfo.getTorque())
                    .put("createTime", carInfo.getCreateTime())
                    .put("updateTime", carInfo.getUpdateTime());
            array = array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @GetMapping("/search") // 模糊查詢
    @ResponseBody
    public String searchByKeyword(@RequestParam(name = "keyword") String keyword) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Carinfo> carInfos = carInfoService.fuzzySearch(keyword);

        for (Carinfo carInfo : carInfos) {
            JSONObject item = new JSONObject()
                    .put("id", carInfo.getId())
                    .put("brand", carInfo.getBrand())
                    .put("modelName", carInfo.getModelName())
                    .put("suspension", carInfo.getSuspension())
                    .put("door", carInfo.getDoor())
                    .put("passenger", carInfo.getPassenger())
                    .put("rearWheel", carInfo.getRearWheel())
                    .put("gasoline", carInfo.getGasoline())
                    .put("transmission", carInfo.getTransmission())
                    .put("cc", carInfo.getCc())
                    .put("hp", carInfo.getHp())
                    .put("torque", carInfo.getTorque())
                    .put("createTime", carInfo.getCreateTime())
                    .put("updateTime", carInfo.getUpdateTime());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    @PostMapping("/create") // 新增
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        Carinfo carinfo = carInfoService.create(body);
        if (carinfo == null) {
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
        if (!carInfoService.exists(Id)) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不存在");
        } else {
            Carinfo Carinfo = carInfoService.modify(body);
            if (Carinfo == null) {
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

        if (!carInfoService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id不存在");
        } else {
            if (!carInfoService.remove(id)) {
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
