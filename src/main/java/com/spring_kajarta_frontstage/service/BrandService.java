package com.spring_kajarta_frontstage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Brand;
import com.spring_kajarta_frontstage.repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepo;

    // 查單筆
    public Brand findById(Integer id) {
        return brandRepo.findById(id).get();
    }

    // 查全部
    public List<Brand> findAll() {
        return brandRepo.findAll();
    }

}
