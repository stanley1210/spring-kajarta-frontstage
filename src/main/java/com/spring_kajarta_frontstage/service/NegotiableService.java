package com.spring_kajarta_frontstage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Negotiable;
import com.spring_kajarta_frontstage.repository.NegotiableRepository;

@Service
public class NegotiableService {

    @Autowired
    private NegotiableRepository negotiableRepo;

    public Negotiable findById(Integer id) {
        Negotiable negotiableModel = new Negotiable();
        for (Negotiable negotiable : negotiableRepo.findByIdSql(id)) {
            negotiableModel.setCode(negotiable.getCode());
            negotiableModel.setPercent(negotiable.getPercent());
            negotiableModel.setNegotiable(negotiable.getNegotiable());
        }
        return negotiableModel;
    }
}
