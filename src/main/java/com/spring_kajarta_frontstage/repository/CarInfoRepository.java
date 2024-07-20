package com.spring_kajarta_frontstage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Carinfo;

@Repository
public interface CarInfoRepository extends JpaRepository<Carinfo, Integer> {

    @Query(value = "SELECT * FROM Carinfo "
            + "WHERE id LIKE %:word% "
            + "OR brand LIKE %:word% "
            + "OR model_name LIKE %:word% "
            + "OR suspension LIKE %:word% "
            + "OR door LIKE %:word% "
            + "OR passenger LIKE %:word% "
            + "OR rear_wheel LIKE %:word% "
            + "OR gasoline LIKE %:word% "
            + "OR transmission LIKE %:word% "
            + "OR cc LIKE %:word% "
            + "OR hp LIKE %:word% "
            + "OR torque LIKE %:word% "
            + "OR create_time LIKE %:word% "
            + "OR update_time LIKE %:word% ", nativeQuery = true)
    List<Carinfo> fuzzySearch(@Param(value = "word") String word); // 模糊查詢

}
