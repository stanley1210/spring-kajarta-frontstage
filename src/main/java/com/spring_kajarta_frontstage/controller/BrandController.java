package com.spring_kajarta_frontstage.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Brand;
import com.spring_kajarta_frontstage.service.BrandService;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("find/{id}")
    public String findById(@PathVariable(name = "id") Integer Id) {
        Brand brand = brandService.findById(Id);
        JSONObject responseBody = new JSONObject()
                .put("brand", brand.getBrand())
                .put("trademark", brand.getTrademark());
        return responseBody.toString();
    }

    @GetMapping("/findAll")
    public String findAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        for (Brand brand : brandService.findAll()) {
            JSONObject item = new JSONObject()
                    .put("brand", brand.getBrand())
                    .put("trademark", brand.getTrademark());
            array.put(item);
        }
        return responseBody.put("brandList", array).toString();
    }
}
