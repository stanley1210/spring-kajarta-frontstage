package com.spring_kajarta_frontstage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Negotiable;

@Repository
public interface NegotiableRepository extends JpaRepository<Negotiable, Integer> {

    @Query(value = "SELECT id, negotiable, code, [percent] FROM negotiable WHERE id = :id", nativeQuery = true)
    List<Negotiable> findByIdSql(Integer id);

}
