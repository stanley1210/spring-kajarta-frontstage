package com.spring_kajarta_frontstage.repository;

import com.kajarta.demo.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

}
