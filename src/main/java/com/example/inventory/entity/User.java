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
@Table(name = "\"user\"")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;    
}
