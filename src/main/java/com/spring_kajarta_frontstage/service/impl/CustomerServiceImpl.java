package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.vo.CustomerVO;
import com.spring_kajarta_frontstage.repository.CustomerRepository;
import com.spring_kajarta_frontstage.service.CarService;
import com.spring_kajarta_frontstage.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j // 用於寫log
@Validated // 驗證參數
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    /**
     * 查詢單筆，依據用戶id查詢單一用戶資訊
     * @param customerId
     * @return Customer
     */
    @Override
    public Customer findById(Integer customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        return customer.orElse(null);
    }

    @Override
    public Customer create(Customer customer) {
        Customer newCustomer = new Customer();
        customer = customerRepo.save(customer);
        BeanUtils.copyProperties(customer, newCustomer); // 將傳入的 Customer 屬性複製到新的實體
        return newCustomer; // 保存新的實體並返回
    }


}
