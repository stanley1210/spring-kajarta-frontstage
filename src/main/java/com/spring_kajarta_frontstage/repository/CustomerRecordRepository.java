package com.spring_kajarta_frontstage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kajarta.demo.model.CustomerRecord;

public interface CustomerRecordRepository extends MongoRepository<CustomerRecord, Integer> {
    // 可以定義自訂的查詢方法
}
