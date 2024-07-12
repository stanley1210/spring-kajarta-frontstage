package com.spring_kajarta_frontstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.kajarta.demo.model.Carinfo;
import com.spring_kajarta_frontstage.repository.CarInfoRepository;

@Service
public class CarInfoService {

    @Autowired
    private CarInfoRepository carinfoRepo;

    public List<Carinfo> select() { // 查全部
        return carinfoRepo.findAll();
    }

    public Carinfo findById(Integer id) { // 查單筆ID
        Optional<Carinfo> optional = carinfoRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public void remove(Integer id) { // 刪除單筆
        carinfoRepo.deleteById(id);
    }

    public Carinfo create(Carinfo carinfo) { // 新增
        return carinfoRepo.save(carinfo);
    }
}
