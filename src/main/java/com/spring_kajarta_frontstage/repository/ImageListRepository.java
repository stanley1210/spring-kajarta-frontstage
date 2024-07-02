package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.ImageList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageListRepository extends JpaRepository<ImageList, Integer> {

}
