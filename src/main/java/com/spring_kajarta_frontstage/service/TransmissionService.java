package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Transmission;
import com.spring_kajarta_frontstage.repository.TransmissionRepository;

@Service
public class TransmissionService {

    @Autowired
    private TransmissionRepository transmissionRepo;

    public Transmission findById(Integer id) {
        Transmission transmissionModel = transmissionRepo.findById(id).get();
        String trans = transmissionRepo.findById(id).get().getTrans().trim();
        transmissionModel.setTrans(trans);
        return transmissionModel;
    }
}
