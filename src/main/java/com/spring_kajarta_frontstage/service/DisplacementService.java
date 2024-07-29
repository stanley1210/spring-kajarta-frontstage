package com.spring_kajarta_frontstage.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Displacement;
import com.spring_kajarta_frontstage.repository.DisplacementRepository;

@Service
public class DisplacementService {

    @Autowired
    private DisplacementRepository displacementRepo;

    public Displacement findById(Integer id) {
        Optional<Displacement> displacement = displacementRepo.findById(id);
        if (displacement.isPresent()) {
            System.out.println("displacement=" + displacement.get());
            return displacement.get();
        } else {
            throw new NoSuchElementException("No value present for ID: " + id);
        }
    }
}
