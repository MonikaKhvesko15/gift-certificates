package com.epam.esm.link;

public interface LinkBuilder<T> {
    void toModel(T entity);
}
