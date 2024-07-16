package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Customer;
import com.kajarta.demo.vo.CustomerVO;

import java.util.List;

public interface CustomerService {
    // 計算數量
    long countCustomers();

    // 查詢全部
    List<CustomerVO> findAll();

    // 單筆查詢，依據用戶id查詢單一用戶資訊
    Customer findById(Integer customerId);

    // 多條件查詢，依據用戶性別、帳號分類、帳號、城市、姓名、手機、電子信箱
    List<CustomerVO> multiConditionQuery(
            Character sex,
            Integer accountType,
            String account,
            Integer city,
            String name,
            String phone,
            String email);

    // 新增
    CustomerVO create(CustomerVO customerVO);

    // 修改
    CustomerVO modify(CustomerVO customerVO);



}
