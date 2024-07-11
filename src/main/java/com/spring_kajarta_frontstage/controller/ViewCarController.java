package com.spring_kajarta_frontstage.controller;


import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.ViewCar;
import com.spring_kajarta_frontstage.service.ViewCarService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/front/viewCar")
@CrossOrigin
public class ViewCarController {
@Autowired
private ViewCarService viewCarService;

    //新增
     @PostMapping("/create")
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        ViewCar card = viewCarService.create(body);
                if(card==null) {
                    responseBody.put("success", false);
                    responseBody.put("message", "新增失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "新增成功");
                }
        return responseBody.toString();
    }
    //修改(空)
    //查一
 @GetMapping("/select/{pk}")
    public String findById(@PathVariable(name = "pk") Integer id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();

        ViewCar viewCar = viewCarService.findById(id);
        if(viewCar!=null) {
            String viewCarDate = DatetimeConverter.toString(viewCar.getViewCarDate(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(viewCar.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(viewCar.getUpdateTime(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()

                    .put("id", viewCar.getId())
                    .put("viewTimeSection", viewCar.getViewTimeSection())
                    .put("car", viewCar.getCar().getId())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate",viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("customer", viewCar.getCustomer().getId())
                    .put("createTime",createTime)
                    .put("updateTime", updateTime)
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array = array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }
    //查全
    @GetMapping("/selectAll")
    public String findAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<ViewCar> viewCars = viewCarService.findAll();
        for (ViewCar viewCar : viewCars) {
            String viewCarDate = DatetimeConverter.toString(viewCar.getViewCarDate(), "yyyy-MM-dd");
            String createTime = DatetimeConverter.toString(viewCar.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(viewCar.getUpdateTime(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()
                    .put("id", viewCar.getId())
                    .put("viewTimeSection", viewCar.getViewTimeSection())
                    .put("car", viewCar.getCar().getId())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate", viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("customer", viewCar.getCustomer().getId())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime)
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
    }


    //多條件查詢
    //刪除
//   @DeleteMapping("/delete/{id}")
//     public String remove(@PathVariable Integer id) {
//         JSONObject responseBody = new JSONObject();
//         if(id==null) {
//             responseBody.put("success", false);
//             responseBody.put("message", "Id是必要欄位");
//         } else {
//             if(!productService.exists(id)) {
//                 responseBody.put("success", false);
//                 responseBody.put("message", "Id不存在");
//             } else {
//                 if(!productService.remove(id)) {
//                     responseBody.put("success", false);
//                     responseBody.put("message", "刪除失敗");
//                 } else {
//                     responseBody.put("success", true);
//                     responseBody.put("message", "刪除成功");
//                 }
//             }
//         }
//         return responseBody.toString();
//     }
}
