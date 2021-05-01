package com.epam.esm.util;

import javax.persistence.NoResultException;

@FunctionalInterface
public interface EntityRetriever<T> {
    T retrieve() throws NoResultException;
}
