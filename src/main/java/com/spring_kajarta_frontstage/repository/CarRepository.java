package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kajarta.demo.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
