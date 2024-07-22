package com.spring_kajarta_frontstage.repository.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.kajarta.demo.model.Car;
import com.kajarta.demo.model.Carinfo;
import com.kajarta.demo.model.Preference;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class PreferenceSpecification {

    public static Specification<Car> dynamicSearch(String selectName, Integer productionYear, BigDecimal price,
            Integer milage, Integer score, Integer hp, Double torque) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Car, Carinfo> carinfoJoin = root.join("carinfo", JoinType.LEFT);

            if (selectName != null && !selectName.isEmpty()) {
                predicates.add(cb.like(root.get("selectName"), "%" + selectName + "%"));
            }
            if (productionYear != null) {
                predicates.add(
                        cb.like(cb.function("CAST", String.class, root.get("productionYear"), cb.literal("VARCHAR")),
                                "%" + productionYear + "%"));
            }
            if (price != null) {
                predicates.add(cb.like(cb.function("CAST", String.class, root.get("price"), cb.literal("VARCHAR")),
                        "%" + price + "%"));
            }
            if (milage != null) {
                predicates.add(cb.like(cb.function("CAST", String.class, root.get("milage"), cb.literal("VARCHAR")),
                        "%" + milage + "%"));
            }
            if (score != null) {
                predicates.add(
                        cb.like(cb.function("CAST", String.class, root.get("conditionScore"), cb.literal("VARCHAR")),
                                "%" + score + "%"));
            }
            if (hp != null) {
                predicates.add(cb.like(cb.function("CAST", String.class, carinfoJoin.get("hp"), cb.literal("VARCHAR")),
                        "%" + hp + "%"));
            }
            if (torque != null) {
                predicates.add(
                        cb.like(cb.function("CAST", String.class, carinfoJoin.get("torque"), cb.literal("VARCHAR")),
                                "%" + torque + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
