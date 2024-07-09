package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Integer> {
}
