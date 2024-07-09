package com.spring_kajarta_frontstage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.kajarta.demo.model.Customer;
import com.spring_kajarta_frontstage.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    public Customer findCustomerById(Integer id) {
        Optional<Customer> optional = customerRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
