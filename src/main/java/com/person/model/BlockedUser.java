package com.person.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "blocked_user")
@Data
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "block_user_id")
    private Long blockedUserId;

    @Column(name = "created_at")
    private Date createdAt;
}
