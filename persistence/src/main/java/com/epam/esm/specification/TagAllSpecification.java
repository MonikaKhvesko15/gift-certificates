package com.epam.esm.specification;

import com.epam.esm.entity.Tag;

public class TagAllSpecification implements SqlSpecification<Tag> {
    @Override
    public String getSqlQuery() {
        return "SELECT * FROM tags;";
    }
}
