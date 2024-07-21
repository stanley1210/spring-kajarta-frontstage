package com.spring_kajarta_frontstage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import com.kajarta.demo.model.ViewCar;

@Repository
public interface ViewCarRepository extends JpaRepository<ViewCar, Integer> {

	@Query(value="from ViewCar")
	 List<ViewCar> find3Latest(Pageable pgb);

}
