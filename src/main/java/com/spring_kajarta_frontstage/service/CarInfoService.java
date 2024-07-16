package com.spring_kajarta_frontstage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Carinfo;
import com.spring_kajarta_frontstage.repository.CarInfoRepository;

@Service
public class CarInfoService {
    @Autowired
    private CarInfoRepository carinfoRepo;

    public Carinfo findById(Integer id) { // 查單筆ID
        Optional<Carinfo> optional = carinfoRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
