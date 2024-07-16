package com.spring_kajarta_frontstage.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.enums.ViewTimeSectionEnum;
import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.ViewCar;

import com.spring_kajarta_frontstage.repository.ViewCarRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class ViewCarService {

    @Autowired
    private ViewCarRepository viewCarRepo;
    @Autowired
    private CarService1 carService;
    @Autowired
    private CustomerService customerService;

    // 計算數量
    public long count() {
        return viewCarRepo.count();
    }

    //確定ID存不存在
    public boolean exists(Integer id) {
        if (id != null) {
            return viewCarRepo.existsById(id);
        }
        return false;
    }

    // 新增
    public ViewCar create(String json) {
        try {
            JSONObject obj = new JSONObject(json);

            Integer viewTimeSectionCode  = obj.isNull("viewTimeSection") ? null : obj.getInt("viewTimeSection");
            ViewTimeSectionEnum viewTimeSection = ViewTimeSectionEnum.getByCode(viewTimeSectionCode);
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer salesScore = obj.isNull("salesScore") ? -1 : obj.getInt("salesScore");
            Integer factoryScore = obj.isNull("factoryScore") ? -1 : obj.getInt("factoryScore");
            String viewCarDate = obj.isNull("viewCarDate") ? null : obj.getString("viewCarDate");
            Integer carScore = obj.isNull("carScore") ? -1 : obj.getInt("carScore");
            Integer deal = obj.isNull("deal") ? null : obj.getInt("deal");
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");
            Integer viewCarStatus = obj.isNull("viewCarStatus") ? null : obj.getInt("viewCarStatus");

            Customer customer = customerService.findById(customerId);
            Car car = carService.findById(carId);

            ViewCar insert = new ViewCar();
            insert.setViewTimeSection(viewTimeSection.getCode());
            insert.setCar(car); // Set Car entity
            insert.setSalesScore(salesScore);
            insert.setFactoryScore(factoryScore);
            insert.setViewCarDate(DatetimeConverter.parse(viewCarDate, "yyyy-MM-dd"));
            insert.setCarScore(carScore);
            insert.setDeal(deal);
            insert.setCustomer(customer); // Set Customer entity
            insert.setViewCarStatus(viewCarStatus);

            return viewCarRepo.save(insert);

            return viewCarRepo.save(insert);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改

    public ViewCar modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);

            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer viewTimeSectionCode = obj.isNull("viewTimeSection") ? null : obj.getInt("viewTimeSection");
            ViewTimeSectionEnum viewTimeSection = ViewTimeSectionEnum.getByCode(viewTimeSectionCode);
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer salesScore = obj.isNull("salesScore") ? null : obj.getInt("salesScore");
            Integer factoryScore = obj.isNull("factoryScore") ? null : obj.getInt("factoryScore");
            String viewCarDate = obj.isNull("viewCarDate") ? null : obj.getString("viewCarDate");
            Integer carScore = obj.isNull("carScore") ? null : obj.getInt("carScore");
            Integer deal = obj.isNull("deal") ? null : obj.getInt("deal");
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");
            Integer viewCarStatus = obj.isNull("viewCarStatus") ? null : obj.getInt("viewCarStatus");

            Customer customer = customerService.findById(customerId);
            Car car = carService.findById(carId);

            Optional<ViewCar> optional = viewCarRepo.findById(id);
            if (optional.isPresent()) {
                ViewCar update = optional.get();
                update.setViewTimeSection(viewTimeSection.getCode());
                update.setCar(car); // Set Car entity
                update.setSalesScore(salesScore);
                update.setFactoryScore(factoryScore);
                update.setViewCarDate(DatetimeConverter.parse(viewCarDate, "yyyy-MM-dd"));
                update.setCarScore(carScore);
                update.setDeal(deal);
                update.setCustomer(customer); // Set Customer entity
                update.setViewCarStatus(viewCarStatus);

                return viewCarRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查詢一筆

    public ViewCar findById(Integer id) {
        if (id != null) {
            Optional<ViewCar> optional = viewCarRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
    public ViewCar findById(Integer id) {
        if (id != null) {
            Optional<ViewCar> optional = viewCarRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 查全
    public List<ViewCar> findAll() {
        return viewCarRepo.findAll();
    }

    // 刪除
    public boolean remove(Integer id) {
        if (id != null) {
            Optional<ViewCar> optional = viewCarRepo.findById(id);
            if (optional.isPresent()) {
                viewCarRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

}
