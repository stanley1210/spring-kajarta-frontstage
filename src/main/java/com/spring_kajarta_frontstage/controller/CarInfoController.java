package com.spring_kajarta_frontstage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kajarta.demo.model.Carinfo;
import com.spring_kajarta_frontstage.service.CarInfoService;

// @RestController
// @RequestMapping("/kiajarta")
public class CarInfoController {

    // @Autowired
    // private CarInfoService carInfoService;

    // @GetMapping("/carinfo/list")
    // public ResponseEntity<?> showAllCarinfo(Model model) {
    // List<Carinfo> carinfoList = carInfoService.select();
    // if (carinfoList.isEmpty()) {
    // return ResponseEntity.notFound().build();
    // } else {
    // return ResponseEntity.ok(carinfoList.get(0));
    // }
    // }
}
