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
import jakarta.persistence.criteria.Root;

public class CarSpecification {

    // public static Specification<Car> dynamicSearch(Integer productionYear,
    // BigDecimal price,
    // Integer milage, Integer score) {
    // return (root, query, cb) -> {
    // List<Predicate> predicates = new ArrayList<>();

    // if (productionYear != null) {
    // String proS = productionYear.toString();
    // predicates.add(
    // cb.like(cb.toString(root.get("productionYear")),
    // "%" + proS + "%"));

    // }
    // if (price != null) {
    // String prS = price.toString();
    // predicates.add(cb.like(cb.toString(root.get("price")), "%" + prS + "%"));
    // }
    // if (milage != null) {
    // String miS = milage.toString();
    // predicates.add(
    // cb.like(cb.toString(root.get("milage")),
    // "%" + miS + "%"));

    // }
    // if (score != null) {
    // String scS = score.toString();
    // predicates.add(
    // cb.like(cb.toString(root.get("conditionScore")),
    // "%" + scS + "%"));
    // }

    // return cb.and(predicates.toArray(new Predicate[0]));
    // };
    // }

    public static Specification<Car> dynamicSearch(String modelName, Integer productionYear, BigDecimal price,
            Integer milage, Integer score, Integer hp, Double torque) {
        return (root, query, cb) -> {
            Root<Car> carRoot = root;
            Join<Car, Carinfo> carinfoJoin = carRoot.join("carinfo", JoinType.LEFT);
            Predicate joinCondition = cb.equal(carRoot.get("id"), carinfoJoin.get("id"));
            List<Predicate> predicates = new ArrayList<>();

            // Join<Car, Carinfo> carinfoJoin = root.join("carinfo", JoinType.LEFT);
            // query.where(cb.equal(root.get("id"), carinfoJoin.get("id")));
            // if (modelName != null && !modelName.isEmpty()) {
            // predicates.add(cb.like(carinfoJoin.get("modelName"), "%" + modelName + "%"));
            // }
            // if (productionYear != null) {
            // predicates.add(cb.like(cb.toString(root.get("productionYear")), "%" +
            // productionYear + "%"));
            // }
            // if (price != null) {
            // predicates.add(cb.like(cb.toString(root.get("price")), "%" + price + "%"));
            // }
            // if (milage != null) {
            // predicates.add(cb.like(cb.toString(root.get("milage")), "%" + milage + "%"));
            // }
            // if (score != null) {
            // predicates.add(cb.like(cb.toString(root.get("conditionScore")), "%" + score +
            // "%"));
            // }
            // if (hp != null) {
            // predicates.add(cb.like(cb.toString(carinfoJoin.get("hp")), "%" + hp + "%"));
            // }
            // if (torque != null) {
            // predicates.add(cb.like(cb.toString(carinfoJoin.get("torque")), "%" + torque +
            // "%"));
            // }

            // return cb.and(predicates.toArray(new Predicate[0]));

            predicates.add(joinCondition);
            if (modelName != null && !modelName.isEmpty()) {
                predicates.add(cb.like(carinfoJoin.get("modelName"), "%" + modelName + "%"));
            }
            if (productionYear != null) {
                predicates.add(cb.like(cb.toString(carRoot.get("productionYear")), "%" + productionYear + "%"));
            }
            if (price != null) {
                predicates.add(cb.like(cb.toString(carRoot.get("price")), "%" + price + "%"));
            }
            if (milage != null) {
                predicates.add(cb.like(cb.toString(carRoot.get("milage")), "%" + milage + "%"));
            }
            if (score != null) {
                predicates.add(cb.like(cb.toString(carRoot.get("conditionScore")), "%" + score + "%"));
            }
            if (hp != null) {
                predicates.add(cb.like(cb.toString(carinfoJoin.get("hp")), "%" + hp + "%"));
            }
            if (torque != null) {
                predicates.add(cb.like(cb.toString(carinfoJoin.get("torque")), "%" + torque + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
