package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Passenger;
import com.spring_kajarta_frontstage.repository.PassengerRepository;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepo;

    public Passenger findById(Integer id) {
        Passenger passengerModel = passengerRepo.findById(id).get();
        String seat = passengerRepo.findById(id).get().getSeat().trim();
        passengerModel.setSeat(seat);
        return passengerModel;
    }
}
