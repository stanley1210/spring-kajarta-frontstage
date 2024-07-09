package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Carinfo;

@Repository
public interface CarInfoRepository extends JpaRepository<Carinfo, Integer> {

}
