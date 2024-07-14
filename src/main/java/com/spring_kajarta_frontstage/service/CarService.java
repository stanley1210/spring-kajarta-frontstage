package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.spring_kajarta_frontstage.repository.CarRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepo;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CarInfoService carInfoService;

    public List<Car> findAll(String json) {
        return carRepo.findAll();
    }

    public Optional<Car> findById(Integer Id) {
        Optional<Car> optional = carRepo.findById(Id);
        if (optional.isPresent()) {
            return optional;
        }
        return null;
    }

    public Car save(Car car) {
        return carRepo.save(car);
    }

    // public Car create(String json) {
    // try {
    // JSONObject obj = new JSONObject(json);
    // // Integer ID = obj.isNull("id") ? null : obj.getInt("id");
    // Integer productionYear = obj.isNull("productionYear") ? null :
    // obj.getInt("productionYear");
    // Integer milage = obj.isNull("milage") ? null : obj.getInt("milage");
    // Integer customerId = obj.isNull("customerId") ? null :
    // obj.getInt("customerId");
    // Integer employeeId = obj.isNull("employeeId") ? null :
    // obj.getInt("employeeId");
    // Integer negotiable = obj.isNull("negotiable") ? null :
    // obj.getInt("negotiable");
    // Integer conditionScore = obj.isNull("conditionScore") ? null :
    // obj.getInt("conditionScore");
    // Integer branch = obj.isNull("branch") ? null : obj.getInt("branch");
    // Integer state = obj.isNull("state") ? null : obj.getInt("state");
    // BigDecimal price = obj.isNull("price") ? null : obj.getBigDecimal("price");
    // String launchDate = obj.isNull("launchDate") ? null :
    // obj.getString("launchDate");
    // Integer carinfoId = obj.isNull("carinfoId") ? null : obj.getInt("carinfoId");
    // String color = obj.isNull("color") ? null : obj.getString("color");
    // Integer remark = obj.isNull("remark") ? null : obj.getInt("remark");

    // // Optional<Car> optional = carRepo.findById(ID);
    // // if (optional.isEmpty()) {
    // Car car = new Car();
    // Customer customer = customerService.findById(customerId);
    // Employee employee = employeeService.findById(employeeId);
    // Carinfo carInfo = carInfoService.findById(carinfoId);
    // // car.setId(ID);
    // car.setProductionYear(productionYear);
    // car.setMilage(milage);
    // car.setCustomer(customer);
    // car.setEmployee(employee);
    // car.setNegotiable(negotiable);
    // car.setConditionScore(conditionScore);
    // car.setBranch(branch);
    // car.setState(state);
    // car.setPrice(price);
    // car.setLaunchDate(DatetimeConverter.parse(launchDate, "yyyy-MM-dd"));
    // car.setCarinfo(carInfo);
    // car.setColor(color);
    // car.setRemark(remark);
    // System.out.println(customer);
    // return carRepo.save(car);

    // } catch (Exception e) {

    // e.printStackTrace();
    // }
    // return null;
    // }

    public boolean exists(Integer Id) {
        if (Id != null) {
            return carRepo.existsById(Id);
        }
        return false;
    }

    public boolean remove(Integer id) {
        if (id != null && carRepo.existsById(id)) {
            carRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Car modify(String json) { // 修改
        try {
            JSONObject obj = new JSONObject(json);

            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer productionYear = obj.isNull("productionYear") ? null : obj.getInt("productionYear");
            Integer milage = obj.isNull("milage") ? null : obj.getInt("milage");
            Integer negotiable = obj.isNull("negotiable") ? null : obj.getInt("negotiable");
            Integer conditionScore = obj.isNull("conditionScore") ? null : obj.getInt("conditionScore");
            Integer branch = obj.isNull("branch") ? null : obj.getInt("branch");
            Integer state = obj.isNull("state") ? null : obj.getInt("state");
            BigDecimal price = obj.isNull("price") ? null : obj.getBigDecimal("price");
            String launchDate = obj.isNull("launchDate") ? null : obj.getString("launchDate");
            String color = obj.isNull("color") ? null : obj.getString("color");
            Integer remark = obj.isNull("remark") ? null : obj.getInt("remark");

            Customer c = customerService.findById(id);
            Employee e = employeeService.findById(id);
            Carinfo carinfo = carInfoService.findById(id);

            Optional<Car> optional = carRepo.findById(id);
            if (optional.isPresent()) {
                Car update = optional.get();
                update.setProductionYear(productionYear);
                update.setMilage(milage);
                update.setNegotiable(negotiable);
                update.setConditionScore(conditionScore);
                update.setBranch(branch);
                update.setState(state);
                update.setPrice(price);
                update.setLaunchDate(DatetimeConverter.parse(launchDate, "yyyy-MM-dd"));
                update.setColor(color);
                update.setRemark(remark);
                update.setCustomer(c);
                update.setEmployee(e);
                update.setCarinfo(carinfo);
                return carRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
