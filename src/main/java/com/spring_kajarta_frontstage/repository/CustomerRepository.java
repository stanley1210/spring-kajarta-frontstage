package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{
    // 多條件查詢，依據用戶性別、帳號分類、帳號、城市、姓名、手機、電子信箱
    @Query("SELECT c FROM Customer c WHERE "
            + "(:sex IS NULL OR c.sex = :sex) AND "
            + "(:accountType IS NULL OR c.accountType = :accountType) AND "
            + "(:account IS NULL OR c.account = :account) AND "
            + "(:city IS NULL OR c.city = :city) AND "
            + "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) AND "
            + "(:phone IS NULL OR c.phone = :phone) AND "
            + "(:email IS NULL OR c.email = :email)")
    Page<Customer> findByMultipleConditions(
            @Param("sex") Character sex,
            @Param("accountType") Integer accountType,
            @Param("account") String account,
            @Param("city") Integer city,
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("email") String email,
            Pageable pageable);


    @Query("SELECT c FROM Customer c WHERE c.account = :account")
    Customer findByAccount(@Param("account") String account);

}




