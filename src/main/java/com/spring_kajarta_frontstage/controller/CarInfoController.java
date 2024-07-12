package com.spring_kajarta_frontstage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Carinfo;
import com.spring_kajarta_frontstage.service.CarInfoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/kajarta")
public class CarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @GetMapping("/carinfo/list") // 查全部
    @ResponseBody
    public List<Carinfo> showAll() {
        return carInfoService.select();
    }

    @GetMapping("/carinfo/{id}") // 查單筆
    public ResponseEntity<Carinfo> showById(@PathVariable Integer id) {
        Carinfo carinfo = carInfoService.findById(id);
        if (carinfo != null) {
            return ResponseEntity.ok(carinfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/carinfo") // 新增
    public ResponseEntity<Carinfo> createCarInfo(@RequestBody Carinfo carinfo) {
        System.out.println("Received Carinfo: " + carinfo);
        Carinfo createdCarinfo = carInfoService.create(carinfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarinfo);
    }

}
