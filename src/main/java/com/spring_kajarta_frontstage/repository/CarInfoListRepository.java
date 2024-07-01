package com.spring_kajarta_frontstage.repository;

import org.springframework.stereotype.Repository;
import com.kajarta.demo.CarInfoList;

@Repository
public interface CarInfoListRepository extends JpaRepository<CarInfoList, Integer> {

}
