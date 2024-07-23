package com.spring_kajarta_frontstage.repository.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class CarinfoSpecification {
    public static Specification<Carinfo> dynamicSearch(String modelName, Integer hp, Double torque) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (modelName != null && !modelName.isEmpty()) {
                predicates.add(cb.like(cb.toString(root.get("modelName")), "%" + modelName + "%"));
            }

            if (hp != null) {
                String hpS = hp.toString();
                predicates.add(cb.like(cb.toString(root.get("hp")), "%" + hpS + "%"));
            }
            if (torque != null) {
                String torS = torque.toString();
                predicates.add(cb.like(cb.toString(root.get("torque")), "%" + torS + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
