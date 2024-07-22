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

public class CarSpecification {

    public static Specification<Car> dynamicSearch(String modelName, Integer productionYear, BigDecimal price,
            Integer milage, Integer score, Integer hp, Double torque) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Car, Carinfo> carinfoJoin = root.join("carinfo", JoinType.INNER);

            if (modelName != null && !modelName.isEmpty()) {
                predicates.add(cb.like(carinfoJoin.get("modelName"), "%" + modelName + "%"));
            }
            if (productionYear != null) {
                String proS = productionYear.toString();
                predicates.add(
                        cb.like(cb.toString(root.get("productionYear")),
                                "%" + proS + "%"));

            }
            if (price != null) {
                String prS = price.toString();
                predicates.add(cb.like(cb.toString(root.get("price")), "%" + prS + "%"));
            }
            if (milage != null) {
                String miS = milage.toString();
                predicates.add(
                        cb.like(cb.toString(root.get("milage")),
                                "%" + miS + "%"));

            }
            if (score != null) {
                String scS = score.toString();
                predicates.add(
                        cb.like(cb.toString(root.get("conditionScore")),
                                "%" + scS + "%"));
            }
            if (hp != null) {
                String hpS = hp.toString();
                System.out.println("======================================================");
                System.out.println("hps=" + hpS);
                predicates.add(cb.like(
                        cb.toString(carinfoJoin.get("hp")),
                        "%" + hpS + "%"));
            }
            if (torque != null) {
                String torS = torque.toString();
                predicates.add(
                        cb.like(cb.toString(carinfoJoin.get("torque")),
                                "'%" + torS + "%'"));
            }
            System.out.println("cbArray=" + cb.and(predicates.toArray(new Predicate[0])));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
