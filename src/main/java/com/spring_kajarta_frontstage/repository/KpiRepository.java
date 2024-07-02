package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kajarta.demo.model.Kpi;

public interface KpiRepository extends JpaRepository<Kpi, Integer> {

}
