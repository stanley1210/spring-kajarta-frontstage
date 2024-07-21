package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Suspension;
import com.spring_kajarta_frontstage.repository.SuspensionRepository;

@Service
public class SuspensionService {

    @Autowired
    private SuspensionRepository suspensionRepo;

    public Suspension findById(Integer id) {
        return suspensionRepo.findById(id).get();
    }
}
