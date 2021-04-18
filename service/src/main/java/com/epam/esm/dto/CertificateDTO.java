package com.epam.esm.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CertificateDTO {
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;


    @NotNull
    @Max(value = 150)
    @Min(value = 1)
    private int duration;

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private boolean isDeleted;
    @Valid
    private Set<TagDTO> tags = new HashSet<>();

    public CertificateDTO() {
    }

    public CertificateDTO(Long id,
                          String name,
                          String description,
                          BigDecimal price,
                          int duration,
                          LocalDateTime createDate,
                          LocalDateTime lastUpdateDate,
                          boolean isDeleted, Set<TagDTO> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.isDeleted = isDeleted;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public boolean getDeletedStatus() {
        return isDeleted;
    }

    public void setDeletedStatus(boolean deleted) {
        isDeleted = deleted;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateDTO that = (CertificateDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
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
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, isDeleted, tags);
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
