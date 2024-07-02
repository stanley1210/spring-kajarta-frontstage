package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kajarta.demo.model.Carinfo;

public interface CarInfoRepository extends JpaRepository<Carinfo, Integer> {

}
