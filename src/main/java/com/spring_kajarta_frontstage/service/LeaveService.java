package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Leave;
import com.kajarta.demo.vo.LeaveVO;


public interface LeaveService {
    // 單筆查詢，依據請假id查詢單一請假資訊
    Leave findById(Integer leaveId);

    // 新增
    LeaveVO create(LeaveVO leaveVO);
}
