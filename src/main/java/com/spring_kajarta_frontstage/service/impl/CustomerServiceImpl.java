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
     * @return CustomerVO
     */
    @Override
    public CustomerVO findById(Integer customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (customer.isEmpty()){
            return null;
        }
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer.get(), customerVO);
        return customerVO;
    }


}
