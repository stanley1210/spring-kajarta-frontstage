package com.spring_kajarta_frontstage.controller;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Brand;
import com.spring_kajarta_frontstage.service.BrandService;

@RestController
@RequestMapping("/enum")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brand/{id}")
    public String deCode(@PathVariable(name = "id") Integer Id) {
        Brand brand = brandService.findById(Id);
        JSONObject responseBody = new JSONObject()
                .put("brand", brand.getBrand())
                .put("trademark", brand.getTrademark());
        return responseBody.toString();
    }
}
