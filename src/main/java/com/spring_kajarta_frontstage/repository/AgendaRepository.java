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
                        + "(:unavailableTimeStr IS NULL OR agd.unavailableTimeStr >= :unavailableTimeStr) AND "
                        + "(:createTime IS NULL OR agd.createTime >= :createTime) AND "
                        + "(:unavailableTimeEnd IS NULL OR agd.unavailableTimeEnd < :unavailableTimeEnd) AND "
                        + "(:unavailableStatus IS NULL OR agd.unavailableStatus = :unavailableStatus) AND "
                        + "(:ckeckavailableTimeStr IS NULL OR :ckeckavailableTimeEnd IS NULL OR (agd.unavailableTimeStr BETWEEN :ckeckavailableTimeStr AND :ckeckavailableTimeEnd) OR (agd.unavailableTimeEnd BETWEEN :ckeckavailableTimeStr AND :ckeckavailableTimeEnd))")
        public Page<Agenda> findByHQL(@Param("id") Integer id,
                        @Param("employee") Employee employee,
                        @Param("unavailableTimeStr") Date unavailableTimeStr,
                        @Param("createTime") Date createTime,
                        @Param("unavailableTimeEnd") Date unavailableTimeEnd,
                        @Param("unavailableStatus") Integer unavailableStatus,
                        @Param("ckeckavailableTimeStr") Date ckeckavailableTimeStr,
                        @Param("ckeckavailableTimeEnd") Date ckeckavailableTimeEnd,
                        Pageable pageable);

}
