package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kajarta.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{
    
}



