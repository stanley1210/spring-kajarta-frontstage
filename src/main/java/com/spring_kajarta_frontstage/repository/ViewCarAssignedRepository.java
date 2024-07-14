package com.spring_kajarta_frontstage.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Employee;
import com.kajarta.demo.model.ViewCar;
import com.kajarta.demo.model.ViewCarAssigned;

@Repository
public interface ViewCarAssignedRepository extends JpaRepository<ViewCarAssigned, Integer> {
        @Query("SELECT vca FROM ViewCarAssigned vca WHERE (:id IS NULL OR vca.id = :id) AND "
                        + "(:viewCarDateStr IS NULL OR :viewCarDateEnd IS NULL OR vca.viewCar.viewCarDate BETWEEN :viewCarDateStr AND :viewCarDateEnd ) AND "
                        + "(:employee IS NULL OR vca.employee = :employee) AND "
                        + "(:teamLeaderId IS NULL OR vca.teamLeaderId = :teamLeaderId) AND "
                        + "(:viewCar IS NULL OR vca.viewCar = :viewCar) AND "
                        + "(:car IS NULL OR vca.viewCar.car = :car) AND "
                        + "(:carinfo IS NULL OR vca.viewCar.car.carinfo = :carinfo) AND "
                        + "(:model IS NULL OR vca.viewCar.car.carinfo.model = :model) AND "
                        + "(:assignedStatus IS NULL OR vca.assignedStatus = :assignedStatus) ")
        public Page<ViewCarAssigned> findByHQL(@Param("id") Integer id,
                        @Param("viewCarDateStr") Date viewCarDateStr,
                        @Param("viewCarDateEnd") Date viewCarDateEnd,
                        @Param("employee") Employee employee,
                        @Param("teamLeaderId") Integer teamLeaderId,
                        @Param("viewCar") ViewCar viewCar,
                        @Param("car") Car car,
                        @Param("carinfo") Carinfo carinfo,
                        @Param("model") Integer model,
                        @Param("assignedStatus") Integer assignedStatus,
                        Pageable pageable);
}
