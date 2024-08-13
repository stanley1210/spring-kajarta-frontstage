package com.spring_kajarta_frontstage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.ViewCar;

@Repository
public interface ViewCarRepository extends JpaRepository<ViewCar, Integer> {

	@Query(value = "from ViewCar")
	List<ViewCar> find3Latest(Pageable pgb);

	List<ViewCar> findByCustomer_Id(Integer customerId); // 根據 customerId 查找所有 ViewCar

	Page<ViewCar> findPageByCustomerId(Integer customerId, Pageable pageable);

	@Query(value = "SELECT vc.* FROM view_car vc JOIN car c ON (vc.car_id = c.id) WHERE sales_score > 0 AND car_id=:carId", nativeQuery = true)
	List<ViewCar> findSalesScoreByCarId(Integer carId); // 搜尋銷售員負責的車

	@Query(value = "select view_car.* from view_car join view_car_assigned on view_car.id=view_car_assigned.view_car_id where employee_id=:employeeId And sales_score>=0", nativeQuery = true)
	List<ViewCar> findViewCarByEmployeeId(Integer employeeId); // 搜尋銷售員負責的車

	ViewCar findTopByOrderByIdDesc();// 查詢最新ID
}
