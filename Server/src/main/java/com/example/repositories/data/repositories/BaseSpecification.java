package com.example.repositories.data.repositories;

import org.springframework.data.jpa.domain.Specification;

public class BaseSpecification {
    //exactly 
    public static <T> Specification<T> hasPropertyEqual(String propertyName, Object propertyValue) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(propertyName), propertyValue);
    }
    //suitable
    public static <T> Specification<T> hasPropertyLike(String propertyName, String pattern) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(propertyName), pattern);
    }
    
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
