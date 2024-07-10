package com.spring_kajarta_frontstage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Preference;
import com.spring_kajarta_frontstage.repository.PreferenceRepository;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preRepo;

    public List<Preference> selectPreference() { // 查全部
        return preRepo.findAll();
    }

}
