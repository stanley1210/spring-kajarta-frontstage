package com.spring_kajarta_frontstage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kajarta.demo.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Page<Like> findByCustomerId(Integer customerId, Pageable pageable);
}
