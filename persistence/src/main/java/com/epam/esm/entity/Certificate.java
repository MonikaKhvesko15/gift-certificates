package com.epam.esm.entity;

import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class Certificate implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;
    private List<Tag> tags;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public LocalDateTime getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(LocalDateTime last_update_date) {
        this.last_update_date = last_update_date;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean AddTag(Tag tag) {
        if (tags == null) {
            tags = new ArrayList<>();
            tags.add(tag);
            return true;
        } else {
            if (tags.contains(tag)) {
                return false;
            }
        }
        tags.add(tag);
        return true;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", create_date=" + create_date +
                ", last_update_date=" + last_update_date +
                ", tags=" + tags +
                '}';
    }
}
