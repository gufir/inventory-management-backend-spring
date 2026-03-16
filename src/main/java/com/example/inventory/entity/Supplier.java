package com.example.inventory.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "supplier")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Supplier {
    @Id
    @Column (name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column (name = "supplier_name", nullable = false)
    private String supplierName;

    @Column (name = "address", nullable = false)
    private String address;

    // @Column(name = "email", nullable = false)
    // private String email;

    @Column (name = "phone", nullable = false)
    private String phone;

    @Column (name = "created_at", nullable = false)
    private Instant createdAt;

    @Column (name = "created_by", nullable = false)
    private UUID createdBy;

    @Column (name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column (name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column (name = "deleted_at", nullable = false)
    private Instant deletedAt;

    @Column (name = "deleted_by", nullable = false)
    private UUID deletedBy;
}
