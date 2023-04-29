package com.orderandwarehouse.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "storage_unit")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StorageUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private Component component;
    @NotNull
    @Column(name = "[row]")
    private Integer row;
    @NotNull
    @Column(name = "[column]")
    private Integer column;
    @NotNull
    private Integer shelf;
    private Double quantity;
    @Column(name = "[full]")
    private boolean full;
    @NotNull
    private LocalDateTime dateAdded;
    @NotNull
    private LocalDateTime dateModified;

    public boolean isEmpty() {
        return component == null && (quantity == null || quantity == 0);
    }
}
