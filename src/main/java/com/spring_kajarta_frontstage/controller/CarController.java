package com.spring_kajarta_frontstage.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.spring_kajarta_frontstage.service.CarInfoService;
import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.CustomerService;
import com.spring_kajarta_frontstage.service.EmployeeService;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CarInfoService carInfoService;

    // 查全部
    @GetMapping("/findAll")
    public String findAll() {
        List<Car> carList = carService.findAll();
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Car car : carList) {
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
                    .put("remark", car.getRemark());
            array.put(item);
        }
        return responseBody.put("list", array).toString();
    }

    // 查詢單筆
    @GetMapping("/find/{Id}")
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("false", false);
            responseBody.put("message", "ID不得為空");
        } else {
            Optional<Car> carOptional = carService.findById(Id);
            if (carOptional != null) {
                Car carModel = carOptional.get();
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
                responseBody.put("false", false);
                responseBody.put("message", "ID不存在");
            }
        }
        return responseBody.toString();
    }

    @PostMapping("/create")
    public Car create(@RequestBody Car car) {
        Customer c = customerService.findById(1);
        Employee e = employeeService.findById(1);
        Carinfo carinfo = carInfoService.findById(1);
        car.setCustomer(c);
        car.setEmployee(e);
        car.setCarinfo(carinfo);

        car.setId(0);
        return carService.save(car);

    }

    @PostMapping("/create2")
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
