package com.epam.esm.entity;

import java.util.Objects;


public class Tag extends Entity {

    private String name;

    public enum Columns {
        ID("id"),
        NAME("name"),
        TAG_ID("tag_id");

        private String column;

        Columns(String columnName) {
            this.column = columnName;
        }

        public String getColumn() {
            return column;
        }
    }

    public Tag() {
    }

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
