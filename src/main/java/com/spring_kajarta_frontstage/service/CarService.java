package com.spring_kajarta_frontstage.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Employee;
import com.spring_kajarta_frontstage.repository.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer ID = obj.isNull("id") ? null : obj.getInt("id");
            Integer productionYear = obj.isNull("productionYear") ? null : obj.getInt("productionYear");
            Integer milage = obj.isNull("milage") ? null : obj.getInt("milage");
            Customer customer = obj.isNull("customer") ? null : obj.getClass("customer");
            Employee employee = obj.isNull("fwp") ? null : obj.getLong("fwp");
            Short negotiable = obj.isNull("fwa") ? null : obj.getLong("fwa");
            Integer conditionScore = obj.isNull("fwn") ? null : obj.getLong("fwn");
            Short branch = obj.isNull("gadn") ? null : obj.getLong("gadn");
            Short state = obj.isNull("odc") ? null : obj.getLong("odc");
            BigDecimal price = obj.isNull("oduc") ? null : obj.getLong("oduc");
            Instant launchDate = obj.isNull("oduc") ? null : obj.getLong("oduc");
            Carinfo carinfo = obj.isNull("oduc") ? null : obj.getLong("oduc");
            String color = obj.isNull("oduc") ? null : obj.getLong("oduc");
            Short remark = obj.isNull("oduc") ? null : obj.getLong("oduc");
            Instant createTime = obj.isNull("oduc") ? null : obj.getLong("oduc");
            Instant updateTime = obj.isNull("oduc") ? null : obj.getLong("oduc");

            Optional<TpeData> optional = tdRepo.findById(ID);
            if (optional.isEmpty()) {
                TpeData td = new TpeData();
                td.setID(ID);
                td.setYears(Years);
                td.setVisitsGovWebNum(VisitsGovWebNum);
                td.setTaipeiPassNum(TaipeiPassNum);
                td.setFreeWifiPlace(FreeWifiPlace);
                td.setFreeWifiAccount(FreeWifiAccount);
                td.setFreeWifiNum(FreeWifiNum);
                td.setGovAppDownloadNum(GovAppDownloadNum);
                td.setOpenDataCount(OpenDataCount);
                td.setOpenDataUseCount(OpenDataUseCount);
                return tdRepo.save(td);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
