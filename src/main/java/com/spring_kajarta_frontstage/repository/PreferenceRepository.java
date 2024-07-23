package com.spring_kajarta_frontstage.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Preference;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Integer>, JpaSpecificationExecutor<Car> {

        @Query(value = "SELECT * FROM Preference "
                        + "WHERE id LIKE %:word% "
                        + "OR select_name LIKE %:word% "
                        + "OR production_year LIKE %:word% "
                        + "OR price LIKE %:word% "
                        + "OR milage LIKE %:word% "
                        + "OR score LIKE %:word% "
                        + "OR customer_id LIKE %:word% "
                        + "OR carinfo_id LIKE %:word% "
                        + "OR brand LIKE %:word% "
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
                        + "OR update_time LIKE %:word% "
                        + "OR preferences_lists LIKE %:word% ", nativeQuery = true)
        List<Preference> fuzzySearch(@Param(value = "word") String word); // 模糊查詢全部

        @Query(value = "SELECT * FROM Preference WHERE select_name LIKE %:word%", nativeQuery = true)
        List<Preference> searchBySelectName(@Param(value = "word") String word); // 鎖定select_name欄位

        @Query(value = "SELECT * FROM Preference WHERE production_year LIKE %:word%", nativeQuery = true)
        List<Preference> searchByProductionYear(@Param(value = "word") Integer word); // 鎖定production_year欄位

        @Query(value = "SELECT * FROM Preference WHERE price LIKE %:word%", nativeQuery = true)
        List<Preference> searchByPrice(@Param(value = "word") BigDecimal word); // 鎖定price欄位

        @Query(value = "SELECT * FROM Preference WHERE milage LIKE %:word%", nativeQuery = true)
        List<Preference> searchByMilage(@Param(value = "word") Integer word); // 鎖定milage欄位

        @Query(value = "SELECT * FROM Preference WHERE score LIKE %:word%", nativeQuery = true)
        List<Preference> searchByScore(@Param(value = "word") Integer word); // 鎖定score欄位

        @Query(value = "SELECT * FROM Preference WHERE hp LIKE %:word%", nativeQuery = true)
        List<Preference> searchByHp(@Param(value = "word") Integer word); // 鎖定hp欄位

        @Query(value = "SELECT * FROM Preference WHERE torque LIKE %:word%", nativeQuery = true)
        List<Preference> searchByTorque(@Param(value = "word") String word); // 鎖定torque欄位

}
