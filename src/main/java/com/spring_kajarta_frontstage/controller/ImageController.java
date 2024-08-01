package com.spring_kajarta_frontstage.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Image;
import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.ImageService;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private CarService carService;

    // 查全部
    @GetMapping("/findAll")
    public String findAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Image image : imageService.findAll()) {
            String imageUrl = "/kajarta/image/getImage/" + image.getId();
            JSONObject item = new JSONObject()
                    .put("id", image.getId())
                    .put("image", imageUrl)
                    .put("car", image.getCar().getId())
                    .put("createTime", image.getCreateTime())
                    .put("updateTime", image.getUpdateTime())
                    .put("isListPic", image.getIsListPic())
                    .put("isMainPic", image.getIsMainPic());
            array.put(item);
        }
        return responseBody.put("imageList", array).toString();
    }

    // 查是否為主圖
    @GetMapping(path = "/isMainPic/{carId}")
    public String isMainPic(@PathVariable(name = "carId") Integer photoid) {
        JSONObject responseBody = new JSONObject();
        Image image = imageService.findIsMainPic(photoid);
        return responseBody.put("isMainPic", image.getId()).toString();
    }

    // 查是否為清單圖
    @GetMapping(path = "/isListPic/{carId}")
    public String isListPic(@PathVariable(name = "carId") Integer photoid) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Image image : imageService.findIsListPic(photoid)) {
            JSONObject item = new JSONObject()
                    .put("imageId", image.getId());
            array.put(item);
        }
        return responseBody.put("isListPic", array).toString();
    }

    // 查詢單筆
    @GetMapping("/find/{Id}")
    @ResponseBody
    public String findDataById(@PathVariable(name = "Id") Integer Id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            if (!imageService.exists(Id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                Image image = imageService.findById(Id);
                if (image != null) {
                    String imageUrl = "/kajarta/image/getImage/" + Id;
                    JSONObject item = new JSONObject()
                            .put("id", image.getId())
                            .put("image", imageUrl)
                            .put("car", image.getCar().getId())
                            .put("createTime", image.getCreateTime())
                            .put("updateTime", image.getUpdateTime())
                            .put("isListPic", image.getIsListPic())
                            .put("isMainPic", image.getIsMainPic());
                    array = array.put(item);
                }
            }
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 顯示單張圖片
    @GetMapping(path = "/getImage/{Id}", produces = { MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody byte[] getImage(@PathVariable(name = "Id") Integer photoid) {
        Image image = imageService.findById(photoid);
        byte[] result = image.getImage();
        return result;
    }

    // 以CarId顯示圖片(多張)
    @GetMapping(path = "/getCarIdImage/{carId}")
    public String getCarIdImage(@PathVariable(name = "carId") Integer photoid) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Image image : imageService.findByCarId(photoid)) {
            String imageUrl = "/kajarta/image/getImage/" + image.getId();
            JSONObject item = new JSONObject()
                    .put("id", image.getId())
                    .put("image", imageUrl)
                    .put("car", image.getCar().getId())
                    .put("createTime", image.getCreateTime())
                    .put("updateTime", image.getUpdateTime())
                    .put("isListPic", image.getIsListPic())
                    .put("isMainPic", image.getIsMainPic());
            array.put(item);
        }
        return responseBody.put("CarIdImageList", array).toString();
    }

    // 只新增圖片
    @PostMapping("/createImage")
    public String imageCreate(@RequestParam("images") List<MultipartFile> images,
            @RequestParam("carId") Integer carId) {
        JSONObject responseBody = new JSONObject();
        try {
            for (MultipartFile imageFile : images) {
                byte[] imageByte = imageFile.getBytes();
                Car car = carService.findById(carId);
                Image image = new Image();
                image.setImage(imageByte);
                image.setCar(car);
                imageService.create(image);
            }
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗");
        }
        return responseBody.toString();
    }

    // 新增單筆
    @PostMapping("/create")
    public String jsonCreate(@RequestParam("images") List<MultipartFile> images,
            @RequestParam("carId") Integer carId,
            @RequestParam("isListPic") Integer isListPic,
            @RequestParam("isMainPic") Integer isMainPic) {
        JSONObject responseBody = new JSONObject();
        try {
            for (MultipartFile imageFile : images) {
                byte[] imageByte = imageFile.getBytes();
                Car car = carService.findById(carId);
                Image image = new Image();
                image.setImage(imageByte);
                image.setCar(car);
                image.setIsListPic(isListPic);
                image.setIsMainPic(isMainPic);
                imageService.create(image);
            }

            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗");
        }
        return responseBody.toString();
    }

    // 刪除
    @DeleteMapping("/remove/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (!imageService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id不存在");
        } else {
            if (!imageService.remove(id)) {
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
    public String modify(@PathVariable(name = "id") Integer Id,
            @RequestParam(name = "image", required = false) MultipartFile imageFile,
            @RequestParam(name = "carId", required = false) Integer carId,
            @RequestParam(name = "isListPic", required = false) Integer isListPic,
            @RequestParam(name = "isMainPic", required = false) Integer isMainPic) {
        JSONObject responseBody = new JSONObject();
        if (Id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "ID不得為空");
        } else {
            if (!imageService.exists(Id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                try {
                    byte[] imageByte = imageFile.getBytes();

                    JSONObject item = new JSONObject()
                            .put("id", Id)
                            .put("carId", carId)
                            .put("isListPic", isListPic)
                            .put("isMainPic", isMainPic);
                    Image modifyImage = imageService.modify(item.toString(), imageByte);
                    responseBody.put("success", true);
                    responseBody.put("message", "修改成功");
                    responseBody.put("message", modifyImage);
                } catch (Exception e) {
                    e.printStackTrace();
                    responseBody.put("success", false);
                    responseBody.put("message", "修改失敗");
                }
            }
        }
        return responseBody.toString();
    }
}
