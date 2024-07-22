package com.spring_kajarta_frontstage.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kajarta.demo.model.Preference;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Integer> {

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

        @Query(value = "SELECT * FROM Preference WHERE " + // 沒會員多條件查詢
                        "(:selectName IS NULL OR select_name LIKE %:selectName%) AND " +
                        "(:productionYear IS NULL OR production_year LIKE %:productionYear%) AND " +
                        "(:price IS NULL OR price LIKE %:price%) AND " +
                        "(:milage IS NULL OR milage LIKE %:milage%) AND " +
                        "(:score IS NULL OR score LIKE %:score%) AND " +
                        "(:hp IS NULL OR hp LIKE %:hp%) AND " +
                        "(:torque IS NULL OR torque LIKE %:torque%)", nativeQuery = true)
        List<Preference> dynamicSearch(@Param("selectName") String selectName,
                        @Param("productionYear") Integer productionYear,
                        @Param("price") BigDecimal price,
                        @Param("milage") Integer milage,
                        @Param("score") Integer score,
                        @Param("hp") Integer hp,
                        @Param("torque") Double torque);

}
