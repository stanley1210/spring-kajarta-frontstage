package com.spring_kajarta_frontstage.service.impl;


import com.kajarta.demo.model.Leave;
import com.spring_kajarta_frontstage.repository.LeaveRepository;
import com.spring_kajarta_frontstage.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepo;
    /**
     * 查詢單筆，依據請假id查詢單一請假資訊
     * @param leaveId
     * @return LeaveVO
     */
    @Override
    public Leave findById(Integer leaveId) {
        Optional<Leave> leave = leaveRepo.findById(leaveId);
        return leave.orElse(null);
    }
}
