package com.example.inventory.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "item")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Item {
    @Id
    @Column(name = "item_id")
    private UUID itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_code", nullable = false, unique = true)
    private String itemCode;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name= "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_at", nullable = false)
    private Instant deletedAt;

    @Column(name = "deleted_by", nullable = false)
    private UUID deletedBy;

}
