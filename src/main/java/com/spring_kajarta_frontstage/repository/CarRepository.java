package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Preference;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car> {
List<Car> findByCreateTimeAfter(LocalDateTime since);
}
