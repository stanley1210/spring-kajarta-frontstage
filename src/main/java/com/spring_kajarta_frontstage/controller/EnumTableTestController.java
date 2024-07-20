package com.spring_kajarta_frontstage.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Brand;
import com.kajarta.demo.model.Negotiable;
import com.spring_kajarta_frontstage.service.BrandService;
import com.spring_kajarta_frontstage.service.NegotiableService;

@RestController
@RequestMapping("/enum")
@CrossOrigin
public class EnumTableTestController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private NegotiableService negotiableService;

    @GetMapping("/brand/{id}")
    public String brand(@PathVariable(name = "id") Integer Id) {
        Brand brand = brandService.findById(Id);
        JSONObject responseBody = new JSONObject()
                .put("brand", brand.getBrand())
                .put("trademark", brand.getTrademark());
        return responseBody.toString();
    }

    @GetMapping("/negotiable/{id}")
    public String negotiable(@PathVariable(name = "id") Integer Id) {
        Negotiable item = negotiableService.findById(Id);
        return new JSONObject()
                .put("list", item.getPercent()).toString();
    }
}
