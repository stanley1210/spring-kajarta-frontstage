package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

    @Query("select c from Customer c where c.sex = ?1 and c.accountType = ?2")
    List<Customer> findBySexAndAccountType(Character sex, Integer accountType);
}



