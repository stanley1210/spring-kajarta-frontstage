package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_kajarta_frontstage.repository.CarAdjustRepository;
import com.spring_kajarta_frontstage.repository.CarRepository;

@Service
public class CarAdjustService {

    @Autowired
    private CarAdjustRepository carAdjustRepository;

}
