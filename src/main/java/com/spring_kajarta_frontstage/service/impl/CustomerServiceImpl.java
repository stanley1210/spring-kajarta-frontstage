package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.vo.CustomerVO;
import com.spring_kajarta_frontstage.repository.CustomerRepository;
import com.spring_kajarta_frontstage.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j // 用於寫log
@Validated // 驗證參數
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    /**
     * 查詢單筆，依據用戶id查詢單一用戶資訊
     *
     * @param customerId
     * @return Customer
     */
    @Override
    public Customer findById(Integer customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        return customer.orElse(null);
    }

    // 多條件查詢，依據用戶性別、帳號、城市、姓名
    public List<CustomerVO> multiConditionQuery(Character sex, Integer accountType, Integer city, String name, String phone) {
        List<Customer> customers = customerRepo.findByMultipleConditions(sex, accountType, city, name, phone);
        List<CustomerVO> customerVOList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerVO customerVO = new CustomerVO();
            BeanUtils.copyProperties(customer, customerVO);
            customerVOList.add(customerVO);
        }
        return customerVOList;
    }

    // 新增
    @Override
    public CustomerVO create(CustomerVO customerVO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerVO, customer);
        customerRepo.save(customer);
        CustomerVO customerVONew = new CustomerVO();
        BeanUtils.copyProperties(customer, customerVONew);
        return customerVONew;
    }

    // 修改
    @Transactional
    @Override
    public CustomerVO modify(CustomerVO customerVO) {
        try {
            Optional<Customer> optionalCustomer = customerRepo.findById(customerVO.getId());
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                BeanUtils.copyProperties(customerVO, customer,"createTime", "updateTime");
                customerRepo.save(customer);
                CustomerVO updatedCustomerVO = new CustomerVO();
                BeanUtils.copyProperties(customer, updatedCustomerVO);
                return updatedCustomerVO;
            } else {
                return null;
            }
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }
}
