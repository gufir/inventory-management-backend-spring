package com.example.inventory.entity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "stock_movement")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class StockMovement {
    @Id
    @Column(name = "movement_id", nullable = false)
    private UUID movementId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private StockMovementType type;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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
