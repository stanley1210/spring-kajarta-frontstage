package com.spring_kajarta_frontstage.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kajarta.demo.model.Car;
import com.spring_kajarta_frontstage.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepo;

    public Car findCarById(Integer id) {
        Optional<Car> optional = carRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
