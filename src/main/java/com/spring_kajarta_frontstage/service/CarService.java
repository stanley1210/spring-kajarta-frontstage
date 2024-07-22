package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.Like;
import com.kajarta.demo.model.Preference;
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

    // 查全部
    public List<Car> findAll() {
        return carRepo.findAll();
    }

    // 查ID
    public Car findById(Integer Id) {
        Optional<Car> optional = carRepo.findById(Id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    // 新增（spring方法）
    public Car save(Car car) {
        return carRepo.save(car);
    }

    // 新增（json方法）
    public Car create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer productionYear = obj.isNull("productionYear") ? null : obj.getInt("productionYear");
            Integer milage = obj.isNull("milage") ? null : obj.getInt("milage");
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");
            Integer employeeId = obj.isNull("employeeId") ? null : obj.getInt("employeeId");
            Integer negotiable = obj.isNull("negotiable") ? null : obj.getInt("negotiable");
            Integer conditionScore = obj.isNull("conditionScore") ? null : obj.getInt("conditionScore");
            Integer branch = obj.isNull("branch") ? null : obj.getInt("branch");
            Integer state = obj.isNull("state") ? null : obj.getInt("state");
            BigDecimal price = obj.isNull("price") ? null : obj.getBigDecimal("price");
            String launchDate = obj.isNull("launchDate") ? null : obj.getString("launchDate");
            Integer carinfoId = obj.isNull("carinfoId") ? null : obj.getInt("carinfoId");
            String color = obj.isNull("color") ? null : obj.getString("color");
            Integer remark = obj.isNull("remark") ? null : obj.getInt("remark");

            // Optional<Car> optional = carRepo.findById(ID);
            // if (optional.isEmpty()) {
            Car car = new Car();
            Customer customer = customerService.findById(customerId);
            Employee employee = employeeService.findById(employeeId);
            Carinfo carInfo = carInfoService.findById(carinfoId);
            car.setProductionYear(productionYear);
            car.setMilage(milage);
            car.setCustomer(customer);
            car.setEmployee(employee);
            car.setNegotiable(negotiable);
            car.setConditionScore(conditionScore);
            car.setBranch(branch);
            car.setState(state);
            car.setPrice(price);
            car.setLaunchDate(DatetimeConverter.parse(launchDate, "yyyy-MM-dd"));
            car.setCarinfo(carInfo);
            car.setColor(color);
            car.setRemark(remark);
            return carRepo.save(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 確認是否存在
    public boolean exists(Integer Id) {
        if (Id != null) {
            return carRepo.existsById(Id);
        }
        return false;
    }

    // 移除
    public boolean remove(Integer id) {
        if (id != null && carRepo.existsById(id)) {
            carRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // 修改
    public Car modify(String json) {
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

    // 分頁請求
    public Page<Car> findByPage(Integer pageNumber, String sortOrder, Integer max) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(pageNumber - 1, max, direction, "createTime");
        return carRepo.findAll(pageable);
    }
}
