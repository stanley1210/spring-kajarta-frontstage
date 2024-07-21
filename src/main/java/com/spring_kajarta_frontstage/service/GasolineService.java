package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Gasoline;
import com.spring_kajarta_frontstage.repository.GasolineRepository;

@Service
public class GasolineService {

    @Autowired
    private GasolineRepository gasolineRepo;

    public Gasoline findById(Integer id) {
        Gasoline gasolineModel = gasolineRepo.findById(id).get();
        String gaso = gasolineRepo.findById(id).get().getGaso().trim();
        gasolineModel.setGaso(gaso);
        return gasolineModel;
    }
}
