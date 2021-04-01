package com.epam.esm.specification;

public class TagSpecification implements SqlSpecification {
    @Override
    public String getSqlQuery() {
        return "SELECT * FROM tags WHERE id=1";
    }
}
