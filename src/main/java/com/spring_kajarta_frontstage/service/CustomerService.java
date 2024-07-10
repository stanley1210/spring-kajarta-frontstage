package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.vo.CustomerVO;

public interface CustomerService {

    // 單筆查詢，依據用戶id查詢單一用戶資訊
    CustomerVO findById(Integer customerId);
}
