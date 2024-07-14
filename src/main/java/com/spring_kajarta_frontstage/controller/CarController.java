package com.spring_kajarta_frontstage.controller;

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

    @GetMapping("/find/{Id}")
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Car car = carService.findById(Id).get();
        if (car != null) {
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
            array = array.put(item);
        }
        responseBody.put("list", array);
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
        // JSONObject reponseBody = new JSONObject();
        // JSONObject obj = new JSONObject(body);
        // System.out.println("----------------");
        // // Integer id = obj.isNull("id") ? null : obj.getInt("id");

        // Car car = carService.create(body);
        // if (car == null) {
        // reponseBody.put("success", false);
        // reponseBody.put("message", "新增失敗");
        // } else {
        // reponseBody.put("success", true);
        // reponseBody.put("message", "新增成功");

        // }

        // return reponseBody.toString();
    }

    @DeleteMapping("/delete/{id}") // 刪除
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

    @PutMapping("/modify/{id}") // 修改
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
