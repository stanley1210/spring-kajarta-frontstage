package com.spring_kajarta_frontstage.controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.ViewCar;
import com.spring_kajarta_frontstage.service.ViewCarService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/front/viewCar/create")
@CrossOrigin
public class ViewCarController {
@Autowired
private ViewCarService viewCarService;


    //新增
    //  @PostMapping("/create")
//     public String create(@RequestBody String body) {
//         JSONObject responseBody = new JSONObject();
//         JSONObject obj = new JSONObject(body);
//         Integer id = obj.isNull("id") ? null : obj.getInt("id");
//         if(id==null) {
//             responseBody.put("success", false);
//             responseBody.put("message", "Id是必要欄位");
//         } else {
//             if(productService.exists(id)) {
//                 responseBody.put("success", false);
//                 responseBody.put("message", "Id已存在");
//             } else {
//                 ProductBean product = productService.create(body);
//                 if(product==null) {
//                     responseBody.put("success", false);
//                     responseBody.put("message", "新增失敗");
//                 } else {
//                     responseBody.put("success", true);
//                     responseBody.put("message", "新增成功");
//                 }
//             }
//         }
//         return responseBody.toString();
//     }
    //修改
    //查一

 @GetMapping("/front/viewCar/select")
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
                    .put("car", viewCar.getCar())
                    .put("salesScore", viewCar.getSalesScore())
                    .put("factoryScore", viewCar.getFactoryScore())
                    .put("viewCarDate",viewCarDate)
                    .put("carScore", viewCar.getCarScore())
                    .put("deal", viewCar.getDeal())
                    .put("customer", viewCar.getCustomer())
                    .put("createTime",createTime)
                    .put("updateTime", updateTime)
                    .put("viewCarStatus", viewCar.getViewCarStatus());
            array = array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }




    //查全
    //多條件查詢


}
