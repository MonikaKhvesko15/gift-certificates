package com.epam.esm.specification;

public class TagIdSpecification implements SqlSpecification {
    private final String id;

    public TagIdSpecification(String id) {
        this.id = id;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM tags WHERE id=" + id;
    }
}
