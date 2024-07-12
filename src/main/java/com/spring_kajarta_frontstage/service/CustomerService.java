package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.vo.CustomerVO;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerService {

    // 單筆查詢，依據用戶id查詢單一用戶資訊
    Customer findById(Integer customerId);

    // 新增
    CustomerVO create(CustomerVO customerVO);

    // 更新
    @Transactional
    CustomerVO modify(CustomerVO customerVO);



}
