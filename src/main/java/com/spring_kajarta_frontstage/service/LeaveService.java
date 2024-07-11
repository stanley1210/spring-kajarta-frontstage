package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Leave;



public interface LeaveService {
    // 單筆查詢，依據請假id查詢單一請假資訊
    Leave findById(Integer LeaveId);
}
