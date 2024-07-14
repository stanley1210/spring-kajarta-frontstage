package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.vo.CustomerVO;

import java.util.List;

public interface CustomerService {

    // 單筆查詢，依據用戶id查詢單一用戶資訊
    Customer findById(Integer customerId);

    // 單筆查詢，依據用戶id查詢單一用戶資訊
    List<CustomerVO> findAll(Character sex, Integer accountType);

    // 新增
    CustomerVO create(CustomerVO customerVO);

    // 修改
    CustomerVO modify(CustomerVO customerVO);



}
