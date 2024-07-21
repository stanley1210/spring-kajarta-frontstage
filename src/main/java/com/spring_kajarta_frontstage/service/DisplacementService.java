package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Displacement;
import com.spring_kajarta_frontstage.repository.DisplacementRepository;

@Service
public class DisplacementService {

    @Autowired
    private DisplacementRepository displacementRepo;

    public Displacement findById(Integer id) {
        return displacementRepo.findById(id).get();
    }
}
