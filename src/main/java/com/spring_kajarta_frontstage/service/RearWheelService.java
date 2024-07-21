package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Rearwheel;
import com.spring_kajarta_frontstage.repository.RearWheelRepository;

@Service
public class RearWheelService {

    @Autowired
    private RearWheelRepository rearWheelRepo;

    public Rearwheel findById(Integer id) {
        return rearWheelRepo.findById(id).get();
    }
}
