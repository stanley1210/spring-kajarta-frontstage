package com.spring_kajarta_frontstage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Preference;
import com.spring_kajarta_frontstage.repository.PreferenceRepository;


@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepo;

    public Preference findById(Integer id) {
        if (id != null) {
            Optional<Preference> optional = preferenceRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }





}
