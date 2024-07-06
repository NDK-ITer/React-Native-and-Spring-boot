package com.example.business.data.repositories;

import org.springframework.data.jpa.domain.Specification;

public class BaseSpecification {
    
    public static <T, Y extends Comparable<? super Y>> Specification<T> hasPropertyGreaterThan(String propertyName, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(propertyName), value);
    }
    
    public static <T, Y extends Comparable<? super Y>> Specification<T> hasPropertyLessThan(String propertyName, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(propertyName), value);
    }
    
    public static <T, Y extends Comparable<? super Y>> Specification<T> hasPropertyBetween(String propertyName, Y start, Y end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(propertyName), start, end);
    }
}
