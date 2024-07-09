package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
