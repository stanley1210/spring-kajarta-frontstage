package com.spring_kajarta_frontstage.repository;

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
    List<Preference> fuzzySearch(@Param(value = "word") String word); // 模糊查詢

}
