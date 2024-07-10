package com.spring_kajarta_frontstage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kajarta.demo.model.Agenda;
import com.spring_kajarta_frontstage.dao.AgendaDAO;

public interface AgendaRepository extends JpaRepository<Agenda, Integer>, AgendaDAO {

    // @Query("SELECT ")

}
