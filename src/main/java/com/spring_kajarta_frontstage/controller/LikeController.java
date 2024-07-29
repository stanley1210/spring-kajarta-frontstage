package com.spring_kajarta_frontstage.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Like;
import com.kajarta.demo.model.ViewCar;
import com.spring_kajarta_frontstage.service.LikeService;
import com.spring_kajarta_frontstage.service.ViewCarService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

@RestController
@RequestMapping("/front/like")
@CrossOrigin
public class LikeController {
    @Autowired
    private LikeService likeService;

    // 計算數量
    @GetMapping("/count")
    public long count() {
        return likeService.count();
    }

    // 新增
    @PostMapping("/create")
    public String create(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        Like card = likeService.create(body);
        if (card == null) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗");
        } else {
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        }
        return responseBody.toString();
    }

    // 修改

    @PutMapping("/update/{id}")
    public String modify(@PathVariable Integer id, @RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!likeService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                Like product = likeService.modify(body);
                if (product == null) {
                    responseBody.put("success", false);
                    responseBody.put("message", "修改失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "修改成功");
                }
            }
        }
        return responseBody.toString();
    }

    // //查一
    @GetMapping("/select/{pk}")
    public String findById(@PathVariable(name = "pk") Integer id) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();

        Like like = likeService.findById(id);
        if (like != null) {
            String createTime = DatetimeConverter.toString(like.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(like.getUpdateTime(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()
                    .put("id", like.getId())
                    .put("car", like.getCar().getId())
                    .put("customer", like.getCustomer().getId())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime);
            array = array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }

    // 查全
    @GetMapping("/selectAll")
    public String findAll(@RequestParam Integer pageNumber, @RequestParam String sortOrder, @RequestParam Integer max) {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Page<Like> page = likeService.findByPage(pageNumber, sortOrder, max);
        List<Like> likes = page.getContent();
        for (Like like : likes) {
            String createTime = DatetimeConverter.toString(like.getCreateTime(), "yyyy-MM-dd");
            String updateTime = DatetimeConverter.toString(like.getUpdateTime(), "yyyy-MM-dd");
            JSONObject obj = new JSONObject()
                    .put("likeId", like.getId())
                    .put("carId", like.getCar().getId())
                    .put("productionYear", like.getCar().getProductionYear())
                    .put("milage", like.getCar().getMilage())
                    .put("conditionScore", like.getCar().getConditionScore())
                    .put("price", like.getCar().getPrice())
                    .put("modelName", like.getCar().getCarinfo().getModelName())
                    .put("brand", like.getCar().getCarinfo().getBrand())
                    .put("customerId", like.getCustomer().getId())
                    .put("createTime", createTime)
                    .put("updateTime", updateTime);
            array.put(obj);
        }
        responseBody.put("list", array);
        responseBody.put("totalPages", page.getTotalPages());
        responseBody.put("totalElements", page.getTotalElements());
        responseBody.put("currentPage", page.getNumber() + 1); // Page numbers are 0-based, so we add 1
        return responseBody.toString();
    }

    // //多條件查詢
    // //刪除
    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!likeService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!likeService.remove(id)) {
                    responseBody.put("success", false);
                    responseBody.put("message", "刪除失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "刪除成功");
                }
            }
        }
        return responseBody.toString();
    }
}
