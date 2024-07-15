package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{
    // 多條件查詢，依據用戶性別、帳號、城市、姓名
    @Query("SELECT c FROM Customer c WHERE "
            + "(:sex IS NULL OR c.sex = :sex) AND "
            + "(:accountType IS NULL OR c.accountType = :accountType) AND "
            + "(:city IS NULL OR c.city = :city) AND "
            + "(:name IS NULL OR c.name LIKE %:name%)")
    List<Customer> findByMultipleConditions(
            @Param("sex") Character sex,
            @Param("accountType") Integer accountType,
            @Param("city") Integer city,
            @Param("name") String name);


}




