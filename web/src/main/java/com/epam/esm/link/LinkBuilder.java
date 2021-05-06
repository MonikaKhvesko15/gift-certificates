package com.epam.esm.link;

public interface LinkBuilder<T> {
    //todo: inner link
    void toModel(T entity);
}
