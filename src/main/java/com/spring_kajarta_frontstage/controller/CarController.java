package com.spring_kajarta_frontstage.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.kajarta.demo.model.Brand;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Negotiable;
import com.spring_kajarta_frontstage.service.BrandService;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.NegotiableService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private NegotiableService negotiableService;

    // 查全部
    @GetMapping("/findAll")
    public String findAll(@RequestParam Integer pageNumber,
            @RequestParam String sortOrder,
            @RequestParam Integer max) {
        Page<Car> carPage = carService.findByPage(pageNumber, sortOrder, max);
        List<Car> Cars = carPage.getContent();
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Car car : Cars) {
            String createTime = DatetimeConverter.toString(car.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(car.getUpdateTime(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()
                    .put("id", car.getId())
                    .put("productionYear", car.getProductionYear())
                    .put("milage", car.getMilage())
                    .put("customerId", car.getCustomer().getId())
                    .put("employeeId", car.getEmployee().getId())
                    .put("negotiable", car.getNegotiable())
                    .put("conditionScore", car.getConditionScore())
                    .put("branch", car.getBranch())
                    .put("state", car.getState())
                    .put("price", car.getPrice())
                    .put("launchDate", car.getLaunchDate())
                    .put("carinfoId", car.getCarinfo().getId())
                    .put("color", car.getColor())
                    .put("remark", car.getRemark())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime);
            array.put(item);
        }
        responseBody.put("list", array);
        responseBody.put("totalPages", carPage.getTotalPages());
        responseBody.put("totalElements", carPage.getTotalElements());
        return responseBody.toString();
    }

    // 查詢單筆
    @GetMapping("/find/{Id}")
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            Car carBean = carService.findById(Id);
            Carinfo carInfoBean = carInfoService.findById(Id);
            Brand brandEnum = brandService.findById(Id);
            Negotiable negotiableEnum = negotiableService.findById(Id);
            if (carBean != null) {
                Car carModel = carBean;
                String compareUrl = "kajarta/car/compare";
                JSONObject carJson = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("negotiable", negotiableEnum.getPercent())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", carModel.getBranch())
                        .put("state", carModel.getState())
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark())
                        .put("compare", compareUrl)
                        // CarInfo的值
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("carinfoBrand", brandEnum.getBrand())
                        .put("carinfoModelName", carInfoBean.getModelName())
                        .put("carinfoSuspension", carInfoBean.getSuspension())
                        .put("carinfoDoor", carInfoBean.getDoor())
                        .put("carinfoPassenger", carInfoBean.getPassenger())
                        .put("carinfoRearWheel", carInfoBean.getRearWheel())
                        .put("carinfoGasoline", carInfoBean.getGasoline())
                        .put("carinfoTransmission", carInfoBean.getTransmission())
                        .put("carinfoCc", carInfoBean.getCc())
                        .put("carinfoHp", carInfoBean.getHp())
                        .put("carinfoTorque", carInfoBean.getTorque())
                        .put("carinfoCreateTime", carInfoBean.getCreateTime())
                        .put("carinfoUpdateTime", carInfoBean.getUpdateTime());

                array = array.put(carJson);
                responseBody.put("list", array);
                return responseBody.toString();
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "ID不存在");
            }
        }
        return responseBody.toString();
    }

    // ------------------------------------------------------------------------
    // 查詢兩筆(比較) 未完成
    @GetMapping("/compare")
    @ResponseBody
    public String compare(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            Car carOptional = carService.findById(Id);
            if (carOptional != null) {
                Car carModel = carOptional;
                JSONObject item = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("negotiable", carModel.getNegotiable())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", carModel.getBranch())
                        .put("state", carModel.getState())
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark());
                array = array.put(item);
                responseBody.put("list", array);
                return responseBody.toString();
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "ID不存在");
            }
        }

        if (Id == null) {
            responseBody.put("success", "選擇你要比較的車輛");
        } else {
            Car carOptional = carService.findById(Id);
            if (carOptional != null) {
                Car carModel = carOptional;
                JSONObject item = new JSONObject()
                        .put("id", carModel.getId())
                        .put("productionYear", carModel.getProductionYear())
                        .put("milage", carModel.getMilage())
                        .put("customerId", carModel.getCustomer().getId())
                        .put("employeeId", carModel.getEmployee().getId())
                        .put("negotiable", carModel.getNegotiable())
                        .put("conditionScore", carModel.getConditionScore())
                        .put("branch", carModel.getBranch())
                        .put("state", carModel.getState())
                        .put("price", carModel.getPrice())
                        .put("launchDate", carModel.getLaunchDate())
                        .put("carinfoId", carModel.getCarinfo().getId())
                        .put("color", carModel.getColor())
                        .put("remark", carModel.getRemark());
                array = array.put(item);
                responseBody.put("list", array);
                return responseBody.toString();
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "ID不存在");
            }
        }
        return responseBody.toString();
    }
    // ------------------------------------------------------------------------

    // 新增單筆
    @PostMapping("/create")
    public String jsonCreate(@RequestBody String body) {
        JSONObject reponseBody = new JSONObject();
        Car car = carService.create(body);
        if (car == null) {
            reponseBody.put("success", false);
            reponseBody.put("message", "新增失敗");
        } else {
            reponseBody.put("success", true);
            reponseBody.put("message", "新增成功");
        }
        return reponseBody.toString();
    }

    // 刪除
    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (!carService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id不存在");
        } else {
            if (!carService.remove(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "刪除失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "刪除成功");
            }
        }
        return responseBody.toString();
    }

    // 修改
    @PutMapping("/modify/{id}")
    public String modify(@RequestBody String body, @PathVariable(name = "id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        if (!carService.exists(Id)) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不存在");
        } else {
            Car car = carService.modify(body);
            if (car == null) {
                responseBody.put("success", false);
                responseBody.put("message", "修改失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "修改成功");
            }
        }
        return responseBody.toString();
    }
}
