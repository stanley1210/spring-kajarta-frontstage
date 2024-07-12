package com.spring_kajarta_frontstage.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kajarta.demo.model.Agenda;
import com.kajarta.demo.model.Employee;
import com.spring_kajarta_frontstage.dao.AgendaDAO;

public interface AgendaRepository extends JpaRepository<Agenda, Integer>, AgendaDAO {

        @Query("SELECT agd FROM Agenda agd WHERE (:id IS NULL OR agd.id = :id) AND "
                        + "(:employee IS NULL OR agd.employee = :employee) AND "
                        + "(:unavailable_time_str IS NULL OR agd.unavailableTimeStr > :unavailable_time_str) AND "
                        + "(:create_time IS NULL OR agd.createTime > :create_time) AND "
                        + "(:unavailable_time_end IS NULL OR agd.unavailableTimeEnd < :unavailable_time_end) AND "
                        + "(:unavailable_status IS NULL OR agd.unavailableStatus = :unavailable_status)")
        public Page<Agenda> find2(@Param("id") Integer id,
                        @Param("employee") Employee employee,
                        @Param("unavailable_time_str") Date unavailable_time_str,
                        @Param("create_time") Date create_time,
                        @Param("unavailable_time_end") Date unavailable_time_end,
                        @Param("unavailable_status") Integer unavailable_status,
                        Pageable pageable);

}
