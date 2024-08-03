package com.spring_kajarta_frontstage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT id, image, car_id, create_time, update_time, is_list_pic, is_main_pic FROM image WHERE car_id=:id", nativeQuery = true)
    List<Image> findByCarId(Integer id);

    @Query(value = "SELECT * FROM image WHERE car_id=:id AND is_main_pic=1", nativeQuery = true)
    Image findIsMainPic(Integer id);

    @Query(value = "SELECT * FROM image WHERE car_id=:id AND is_list_pic=1", nativeQuery = true)
    List<Image> findIsListPic(Integer id);
}
