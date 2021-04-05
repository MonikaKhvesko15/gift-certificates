package com.epam.esm.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Certificate extends Entity {

    public static final transient String GIFT_CERTIFICATES_TABLE_NAME = "gift_certificates";

    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDate createDate;
    private LocalDate lastUpdateDate;
    private Set<Tag> tags;

    public enum Columns {
        ID("id"),
        NAME("name"),
        DESCRIPTION("description"),
        PRICE("price"),
        DURATION("duration"),
        CREATE_DATE("create_date"),
        LAST_UPDATE_DATE("last_update_date");

        private String column;

        Columns(String columnName) {
            this.column = columnName;
        }

        public String getColumn() {
            return column;
        }
        }

    public Certificate() {
    }

    public static class Builder {
        //Necessary parameters
        private String name;
        private LocalDate createDate;
        private LocalDate lastUpdateDate;

        //optional parameters
        private Long id;
        private String description;
        private Double price;
        private Integer duration;
        private Set<Tag> tags = new HashSet<>();


        public Builder(String name, LocalDate createDate, LocalDate lastUpdateDate) {
            this.name = name;
            this.createDate = createDate;
            this.lastUpdateDate = lastUpdateDate;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder tags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Certificate build() {
            return new Certificate(this);
        }
    }

    private Certificate(Builder builder) {
        super(builder.id);
        name = builder.name;
        description = builder.description;
        price = builder.price;
        duration = builder.duration;
        createDate = builder.createDate;
        lastUpdateDate = builder.lastUpdateDate;
        tags = builder.tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, duration, createDate, lastUpdateDate, tags);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
