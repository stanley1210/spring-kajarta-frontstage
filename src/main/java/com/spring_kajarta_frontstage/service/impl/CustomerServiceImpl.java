package com.spring_kajarta_frontstage.service.impl;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.vo.CustomerVO;
import com.spring_kajarta_frontstage.repository.CustomerRepository;
import com.spring_kajarta_frontstage.service.CustomerService;
import com.spring_kajarta_frontstage.util.DatetimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j // 用於寫log
@Validated // 驗證參數
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public long countCustomers() {
        return customerRepo.count();
    }

    // 查詢全部
    @Override
    public List<CustomerVO> findAll() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerVO> customerVOList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerVO customerVO = new CustomerVO();
            BeanUtils.copyProperties(customer, customerVO);
            customerVO.setCreateTime(DatetimeConverter.toString(new Date(customer.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            customerVO.setUpdateTime(DatetimeConverter.toString(new Date(customer.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            customerVOList.add(customerVO);
        }
        return customerVOList;
    }

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

    // 多條件查詢，依據用戶性別、帳號分類、帳號、城市、姓名、手機、電子信箱
    public List<CustomerVO> multiConditionQuery(Character sex, Integer accountType, String account, Integer city, String name, String phone, String email) {
        List<Customer> customers = customerRepo.findByMultipleConditions(sex, accountType, account, city, name, phone, email);
        List<CustomerVO> customerVOList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerVO customerVO = new CustomerVO();
            BeanUtils.copyProperties(customer, customerVO);
            customerVO.setCreateTime(DatetimeConverter.toString(new Date(customer.getCreateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
            customerVO.setUpdateTime(DatetimeConverter.toString(new Date(customer.getUpdateTime().getTime()), DatetimeConverter.YYYY_MM_DD_HH_MM_SS));
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
