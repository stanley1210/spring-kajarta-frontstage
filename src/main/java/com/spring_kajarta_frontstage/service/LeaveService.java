package com.spring_kajarta_frontstage.service;

import com.kajarta.demo.model.Leave;
import com.kajarta.demo.vo.LeaveVO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface LeaveService {
    // 計算數量
    long countLeaves();

    // 查詢全部
    List<LeaveVO> findAll();

    // 單筆查詢，依據請假id查詢單一請假資訊
    Leave findById(Integer leaveId);

    // 多條件查詢，依據假單的請假或給假狀態、開始時段、結束時段、假種、休假員工、核可主管、核可狀態、使用期限(開始)、使用期限(結束)
    Page<LeaveVO> findByConditionsWithPagination(LeaveVO leaveVO);


    // 新增
    LeaveVO create(LeaveVO leaveVO);

    // 修改
    LeaveVO modify(LeaveVO leaveVO);
}
