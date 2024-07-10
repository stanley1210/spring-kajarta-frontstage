package com.spring_kajarta_frontstage.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kajarta.demo.model.Agenda;
import com.kajarta.demo.model.Employee;
import com.spring_kajarta_frontstage.dao.AgendaDAO;

public interface AgendaRepository extends JpaRepository<Agenda, Integer>, AgendaDAO {

    // @Query("SELECT agd FROM Agenda agd WHERE (agd.id=:id OR :id=null) AND
    // (agd.employee=:employee OR :employee=null) AND
    // (agd.unavailableTimeStr>:unavailable_time_str OR :unavailable_time_str=null
    // )AND (agd.unavailableTimeEnd<:unavailable_time_end OR
    // :unavailable_time_end=null) AND (agd.unavailableStatus=:unavailable_status OR
    // :unavailable_status=null)")
    // public List<Agenda> find2(@Param("id") Integer id, @Param("employee")
    // Employee employee,
    // @Param("unavailable_time_str") Date unavailable_time_str,
    // @Param("unavailable_time_end") Date unavailable_time_end,
    // @Param("create_time") Date create_time,
    // @Param("unavailable_status") Integer unavailable_status);

}
