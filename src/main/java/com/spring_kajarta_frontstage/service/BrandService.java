package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Brand;
import com.spring_kajarta_frontstage.repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepo;

    public Brand findById(Integer id) {
        return brandRepo.findById(id).get();
    }

}
