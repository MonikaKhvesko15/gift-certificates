package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Certificate extends Entity {

    public static final transient String GIFT_CERTIFICATES_TABLE_NAME = "gift_certificates";

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Boolean isDeleted;
    private Set<Tag> tags;

    public enum Columns {
        ID("id"),
        NAME("name"),
        DESCRIPTION("description"),
        PRICE("price"),
        DURATION("duration"),
        CREATE_DATE("create_date"),
        LAST_UPDATE_DATE("last_update_date"),
        IS_DELETED("isDeleted");

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
        private String description;
        private BigDecimal price;
        private Integer duration;


        //optional parameters
        private Long id;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;
        private Boolean isDeleted;
        private Set<Tag> tags = new HashSet<>();


        public Builder(String name, String description, BigDecimal price, Integer duration) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.duration = duration;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder createDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder lastUpdateDate(LocalDateTime lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public Builder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
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
        isDeleted = builder.isDeleted;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getDeletedStatus() {
        return isDeleted;
    }

    public void setDeletedStatus(Boolean deleted) {
        isDeleted = deleted;
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
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, duration, createDate, lastUpdateDate, isDeleted, tags);
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
                ", isDeleted=" + isDeleted +
                ", tags=" + tags +
                '}';
    }
}
