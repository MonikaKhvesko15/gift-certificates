package com.epam.esm.specification;


public class TagAllSpecification implements SqlSpecification {
    @Override
    public String getSqlQuery() {
        return "SELECT * FROM tags;";
    }
}
