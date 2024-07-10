package com.spring_kajarta_frontstage.controller;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kajarta/viewCar")
@CrossOrigin
public class ViewCarController {

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

}
