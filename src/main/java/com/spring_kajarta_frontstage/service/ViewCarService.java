package com.spring_kajarta_frontstage.service;

import java.time.Instant;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.ViewCar;
import com.spring_kajarta_frontstage.repository.CarRepository;
import com.spring_kajarta_frontstage.repository.CustomerRepository;
import com.spring_kajarta_frontstage.repository.ViewCarRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class ViewCarService {

    @Autowired
    private ViewCarRepository viewCarRepo;
    @Autowired
    private CarRepository carRepo;
    @Autowired
    private CustomerRepository customerRepo;

    // 新增
    public ViewCar create(String json) {
        try {
            JSONObject obj = new JSONObject(json);

            Integer viewTimeSection = obj.isNull("viewTimeSection") ? null : obj.getInt("viewTimeSection");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer salesScore = obj.isNull("salesScore") ? null : obj.getInt("salesScore");
            Integer factoryScore = obj.isNull("factoryScore") ? null : obj.getInt("factoryScore");
            String viewCarDate = obj.isNull("viewCarDate") ? null : obj.getString("viewCarDate");
            Integer carScore = obj.isNull("carScore") ? null : obj.getInt("carScore");
            Integer deal = obj.isNull("deal") ? null : obj.getInt("deal");
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");
            Integer viewCarStatus = obj.isNull("viewCarStatus") ? null : obj.getInt("viewCarStatus");

            Short viewTimeSectionShort = viewTimeSection == null ? null : (short) viewTimeSection.intValue();
            Short dealShort = deal == null ? null : (short) deal.intValue();
            Short viewCarStatusShort = viewCarStatus == null ? null : (short) viewCarStatus.intValue();

            // Find the associated entities
            Optional<Car> optionalCar = carRepo.findById(carId);
            Optional<Customer> optionalCustomer = customerRepo.findById(customerId);

                ViewCar insert = new ViewCar();
                insert.setViewTimeSection(viewTimeSectionShort);
                insert.setCar(optionalCar.orElse(null)); // Set Car entity
                insert.setSalesScore(salesScore);
                insert.setFactoryScore(factoryScore);
                insert.setViewCarDate(DatetimeConverter.parse(viewCarDate, "yyyy-MM-dd").toInstant());
                insert.setCarScore(carScore);
                insert.setDeal(dealShort);
                insert.setCustomer(optionalCustomer.orElse(null)); // Set Customer entity
                insert.setViewCarStatus(viewCarStatusShort);

                // 設置創建時間和更新時間
                Instant now = Instant.now();
                insert.setCreateTime(now);
                insert.setUpdateTime(now);

                return viewCarRepo.save(insert);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 刪除

    // 查詢一筆

    // 查詢多筆

}
