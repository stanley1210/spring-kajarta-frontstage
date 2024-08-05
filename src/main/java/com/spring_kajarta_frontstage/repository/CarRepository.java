package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Preference;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car> {
    List<Car> findByCreateTimeAfter(LocalDateTime since);

    @Query(value = "SELECT * FROM Car WHERE customer_id= :Id", nativeQuery = true)
    List<Car> findByCustomerId(Integer Id); // 搜尋會員心儀清單

    @Query(value = "SELECT * FROM Car WHERE employee_id= :Id", nativeQuery = true)
    List<Car> findCarByEmployeeId(Integer Id); // 搜尋銷售員負責的車

}
