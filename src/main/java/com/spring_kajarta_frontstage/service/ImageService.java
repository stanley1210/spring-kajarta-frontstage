package com.spring_kajarta_frontstage.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Image;
import com.spring_kajarta_frontstage.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private CarService carService;

    // 確認是否存在
    public boolean exists(Integer Id) {
        if (Id != null) {
            return imageRepo.existsById(Id);
        }
        return false;
    }

    // 查全部
    public List<Image> findAll() {
        return imageRepo.findAll();
    }

    // 查ID
    public Image findById(Integer Id) {
        Optional<Image> optional = imageRepo.findById(Id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    // 查carID
    public List<Image> findByCarId(Integer Id) {
        // Image imageModel = new Image();
        // for (Image image : imageRepo.findByCarId(Id)) {
        // imageModel.setCar(image.getCar());
        // imageModel.setImage(image.getImage());
        // }

        return imageRepo.findByCarId(Id);
    }

    // 新增（spring方法）
    public Image create(Image image) {
        return imageRepo.save(image);
    }

    // 新增（json方法）
    public Image create2(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            byte[] image = obj.isNull("image") ? null : obj.getString("image").getBytes();
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer isListPic = obj.isNull("isListPic") ? null : obj.getInt("isListPic");
            Integer isMainPic = obj.isNull("isMainPic") ? null : obj.getInt("isMainPic");

            // Optional<Car> optional = carRepo.findById(ID);
            // if (optional.isEmpty()) {
            Image imageModel = new Image();
            Car car = carService.findById(carId);
            imageModel.setImage(image);
            imageModel.setCar(car);
            imageModel.setIsListPic(isListPic);
            imageModel.setIsMainPic(isMainPic);
            return imageRepo.save(imageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 移除
    public boolean remove(Integer id) {
        if (id != null && imageRepo.existsById(id)) {
            imageRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Image modify(String json, byte[] image) { // 修改
        try {
            JSONObject obj = new JSONObject(json);

            Integer id = obj.isNull("id") ? null : obj.getInt("id");
            Integer carId = obj.isNull("carId") ? null : obj.getInt("carId");
            Integer isListPic = obj.isNull("isListPic") ? null : obj.getInt("isListPic");
            Integer isMainPic = obj.isNull("isMainPic") ? null : obj.getInt("isMainPic");
            Car car = carService.findById(carId);

            Optional<Image> optional = imageRepo.findById(id);
            if (optional.isPresent()) {
                Image update = optional.get();
                update.setImage(image);
                update.setCar(car);
                update.setIsListPic(isListPic);
                update.setIsMainPic(isMainPic);

                return imageRepo.save(update);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
