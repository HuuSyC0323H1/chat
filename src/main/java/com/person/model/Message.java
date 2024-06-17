package com.person.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private Date sentAt;

    @Column(name = "received_at")
    private Date receivedAt;

    @Column(name = "read_at")
    private Date readAt;
}
