package com.spring_kajarta_frontstage.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Notice;
import com.kajarta.demo.model.Preference;
import com.kajarta.demo.model.ViewCar;
import com.kajarta.demo.model.ViewCarAssigned;
import com.spring_kajarta_frontstage.repository.NoticeRepository;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepo;
    @Autowired
    private ViewCarService viewCarService;
    @Autowired
    private ViewCarAssignedService viewCarAssignedService;
    @Autowired
    private CarService carService;
    @Autowired
    private PreferenceService preferenceService;

    // 計算數量
    public long count() {
        return noticeRepo.count();
    }

    // 確定ID存不存在
    public boolean exists(Integer id) {
        if (id != null) {
            return noticeRepo.existsById(id);
        }
        return false;
    }

    // 新增
    public Notice create(String json) {
        try {
            JSONObject obj = new JSONObject(json);

            Integer category = obj.isNull("category") ? null : obj.getInt("category");
            Integer viewableNotificationInt = obj.isNull("viewableNotification") ? null : obj.getInt("viewableNotification");
            Short viewableNotification = viewableNotificationInt != null ? viewableNotificationInt.shortValue() : null;
            Integer readStatus = obj.isNull("readStatus") ? null : obj.getInt("readStatus");
            Integer receiver = obj.isNull("receiver") ? null : obj.getInt("receiver");
            Integer accountType = obj.isNull("accountType") ? null : obj.getInt("accountType");
            Integer viewCarId = obj.isNull("viewCarId") ? null : obj.getInt("viewCarId");
            Integer viewCarAssignedId = obj.isNull("viewCarAssignedId") ? null : obj.getInt("viewCarAssignedId");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer preferenceId = obj.isNull("preferenceId") ? null : obj.getInt("preferenceId");

            ViewCar viewCar = null;
            ViewCarAssigned viewCarAssigned = null;
            Car car = null;
            Preference preference = null;

            if (viewCarId != null) {
                viewCar = viewCarService.findById(viewCarId);
            }
            if (viewCarAssignedId != null) {
                viewCarAssigned = viewCarAssignedService.findById(viewCarAssignedId);
            }
            if (carId != null) {
                car = carService.findById(carId);
            }
            if (preferenceId != null) {
                preference = preferenceService.findById(preferenceId);
            }

            Notice insert = new Notice();
            insert.setCategory(category);
            insert.setViewableNotification(viewableNotification);
            insert.setReadStatus(readStatus);
            insert.setReceiver(receiver);
            insert.setAccountType(accountType);
            insert.setViewCar(viewCar);
            insert.setViewCarAssigned(viewCarAssigned);
            insert.setCar(car);
            insert.setPreference(preference);
            return noticeRepo.save(insert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改

    public Notice modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer category = obj.isNull("category") ? null : obj.getInt("category");
            Integer viewableNotificationInt = obj.isNull("viewableNotification") ? null : obj.getInt("viewableNotification");
            Short viewableNotification = viewableNotificationInt != null ? viewableNotificationInt.shortValue() : null;
            Integer readStatus = obj.isNull("readStatus") ? null : obj.getInt("readStatus");
            Integer receiver = obj.isNull("receiver") ? null : obj.getInt("receiver");
            Integer accountType = obj.isNull("accountType") ? null : obj.getInt("accountType");
            Integer viewCarId = obj.isNull("viewCarId") ? null : obj.getInt("viewCarId");
            Integer viewCarAssignedId = obj.isNull("viewCarAssignedId") ? null : obj.getInt("viewCarAssignedId");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer preferenceId = obj.isNull("preferenceId") ? null : obj.getInt("preferenceId");

            ViewCar viewCar = null;
            ViewCarAssigned viewCarAssigned = null;
            Car car = null;
            Preference preference = null;

            if (viewCarId != null) {
                viewCar = viewCarService.findById(viewCarId);
            }
            if (viewCarAssignedId != null) {
                viewCarAssigned = viewCarAssignedService.findById(viewCarAssignedId);
            }
            if (carId != null) {
                car = carService.findById(carId);
            }
            if (preferenceId != null) {
                preference = preferenceService.findById(preferenceId);
            }

            Optional<Notice> optional = noticeRepo.findById(id);
            if (optional.isPresent()) {
                Notice update = optional.get();
                update.setCategory(category);
                update.setViewableNotification(viewableNotification);
                update.setReadStatus(readStatus);
                update.setReceiver(receiver);
                update.setAccountType(accountType);
                update.setViewCar(viewCar);
                update.setViewCarAssigned(viewCarAssigned);
                update.setCar(car);
                update.setPreference(preference);
                return noticeRepo.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查詢一筆

    public Notice findById(Integer id) {
        if (id != null) {
            Optional<Notice> optional = noticeRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 查全
    public List<Notice> findAll() {
        return noticeRepo.findAll();
    }

    // 刪除
    public boolean remove(Integer id) {
        if (id != null) {
            Optional<Notice> optional = noticeRepo.findById(id);
            if (optional.isPresent()) {
                noticeRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

}
