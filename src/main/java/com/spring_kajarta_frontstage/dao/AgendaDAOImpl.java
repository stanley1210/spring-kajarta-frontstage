package com.spring_kajarta_frontstage.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.kajarta.demo.model.Agenda;
import com.spring_kajarta_frontstage.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class AgendaDAOImpl implements AgendaDAO {
    @PersistenceContext
    private Session session;

    public Session getSession() {
        return this.session;
    }

    @Override
    public long count(JSONObject obj) {
        Integer id = obj.isNull("id") ? null : obj.getInt("id");
        Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
        String unavailable_time_str = obj.isNull("unavailable_time_str") ? null : obj.getString("unavailable_time_str");
        String unavailable_time_end = obj.isNull("unavailable_time_end") ? null : obj.getString("unavailable_time_end");
        Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");
        String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");

        CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criterBuilder.createQuery(Long.class);

        // from
        Root<Agenda> table = criteriaQuery.from(Agenda.class);

        // select count(*)
        criteriaQuery = criteriaQuery.select(criterBuilder.count(table));

        // where
        List<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            predicates.add(criterBuilder.equal(table.get("id"), id));
        }
        if (employee_id != null) {
            predicates.add(criterBuilder.equal(table.get("employee_id"), employee_id));
        }
        if (unavailable_time_str != null && unavailable_time_str.length() != 0) {
            java.util.Date date = DatetimeConverter.parse(unavailable_time_str, "yyyy-MM-dd");
            predicates.add(criterBuilder.greaterThanOrEqualTo(table.get("unavailable_time_str"), date));
        }
        if (unavailable_time_end != null && unavailable_time_end.length() != 0) {
            java.util.Date date = DatetimeConverter.parse(unavailable_time_end, "yyyy-MM-dd");
            predicates.add(criterBuilder.lessThanOrEqualTo(table.get("unavailable_time_end"), date));
        }
        if (unavailable_status != null) {
            predicates.add(criterBuilder.equal(table.get("unavailable_status"), unavailable_status));
        }
        if (create_time != null && create_time.length() != 0) {
            java.util.Date date = DatetimeConverter.parse(create_time, "yyyy-MM-dd");
            predicates.add(criterBuilder.greaterThanOrEqualTo(table.get("create_time"), date));
        }

        criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
        Long result = typedQuery.getSingleResult();
        if (result != null) {
            return result;
        } else {
            return 0;
        }
    }

    @Override
    public List<Agenda> find(JSONObject obj) {
        Integer id = obj.isNull("id") ? null : obj.getInt("id");
        Integer employee_id = obj.isNull("employee_id") ? null : obj.getInt("employee_id");
        String business_purpose = obj.isNull("business_purpose") ? null : obj.getString("business_purpose");
        String unavailable_time_str = obj.isNull("unavailable_time_str") ? null : obj.getString("unavailable_time_str");
        String unavailable_time_end = obj.isNull("unavailable_time_end") ? null : obj.getString("unavailable_time_end");
        Integer unavailable_status = obj.isNull("unavailable_status") ? null : obj.getInt("unavailable_status");
        String create_time = obj.isNull("create_time") ? null : obj.getString("create_time");
        String update_time = obj.isNull("update_time") ? null : obj.getString("update_time");

        int start = obj.isNull("start") ? 0 : obj.getInt("start");
        int max = obj.isNull("max") ? 5 : obj.getInt("max");
        boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
        String order = obj.isNull("order") ? "id" : obj.getString("order");

        CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery<Agenda> criteriaQuery = criterBuilder.createQuery(Agenda.class);

        // from
        Root<Agenda> table = criteriaQuery.from(Agenda.class);

        // where
        List<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            predicates.add(criterBuilder.equal(table.get("id"), id));
        }
        if (employee_id != null) {
            predicates.add(criterBuilder.equal(table.get("employee_id"), employee_id));
        }
        if (unavailable_time_str != null && unavailable_time_str.length() != 0) {
            java.util.Date date = DatetimeConverter.parse(unavailable_time_str, "yyyy-MM-dd");
            predicates.add(criterBuilder.greaterThanOrEqualTo(table.get("unavailable_time_str"), date));
        }
        if (unavailable_time_end != null && unavailable_time_end.length() != 0) {
            java.util.Date date = DatetimeConverter.parse(unavailable_time_end, "yyyy-MM-dd");
            predicates.add(criterBuilder.lessThanOrEqualTo(table.get("unavailable_time_end"), date));
        }
        if (unavailable_status != null) {
            predicates.add(criterBuilder.equal(table.get("unavailable_status"), unavailable_status));
        }
        if (create_time != null && create_time.length() != 0) {
            java.util.Date date = DatetimeConverter.parse(create_time, "yyyy-MM-dd");
            predicates.add(criterBuilder.greaterThanOrEqualTo(table.get("create_time"), date));
        }
        criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));

        // order by
        if (dir) {
            criteriaQuery = criteriaQuery.orderBy(criterBuilder.desc(table.get(order)));
        } else {
            criteriaQuery = criteriaQuery.orderBy(criterBuilder.asc(table.get(order)));
        }

        TypedQuery<Agenda> typedQuery = this.getSession().createQuery(criteriaQuery)
                .setFirstResult(start)
                .setMaxResults(max);
        List<Agenda> result = typedQuery.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }

}
