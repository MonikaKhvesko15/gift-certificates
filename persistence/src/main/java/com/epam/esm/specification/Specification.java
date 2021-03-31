package com.epam.esm.specification;

public interface Specification<T> {
    boolean specified(T t);
}
