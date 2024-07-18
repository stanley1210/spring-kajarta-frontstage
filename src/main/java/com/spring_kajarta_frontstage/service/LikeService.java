package com.spring_kajarta_frontstage.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Customer;
import com.kajarta.demo.model.Like;
import com.kajarta.demo.model.ViewCar;
import com.spring_kajarta_frontstage.repository.LikeRepository;
import com.spring_kajarta_frontstage.repository.ViewCarRepository;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CarService carService;
    @Autowired
    private CustomerService customerService;

    // 計算數量
    public long count() {
        return likeRepository.count();
    }

    //確定ID存不存在
    public boolean exists(Integer id) {
        if (id != null) {
            return likeRepository.existsById(id);
        }
        return false;
    }

    // 新增
    public Like create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");

            Customer customer = customerService.findById(customerId);
            Car car = carService.findById(carId);

            Like insert = new Like();
            insert.setCar(car); // Set Car entity
            insert.setCustomer(customer); // Set Customer entity
            return likeRepository.save(insert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改

    public Like modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer customerId = obj.isNull("customerId") ? null : obj.getInt("customerId");

            Customer customer = customerService.findById(customerId);
            Car car = carService.findById(carId);

            Optional<Like> optional = likeRepository.findById(id);
            if (optional.isPresent()) {
                Like update = optional.get();
                update.setCar(car); // Set Car entity
                update.setCustomer(customer); // Set Customer entity
                return likeRepository.save(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查詢一筆

    public Like findById(Integer id) {
        if (id != null) {
            Optional<Like> optional = likeRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // 查全
    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    public Page<Like> findByPage(Integer pageNumber, String sortOrder, Integer max) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(pageNumber - 1, max, direction, "createTime");
        return likeRepository.findAll(pageable);
    }

    // 刪除
    public boolean remove(Integer id) {
        if (id != null) {
            Optional<Like> optional = likeRepository.findById(id);
            if (optional.isPresent()) {
                likeRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

}
