package com.person.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "message_template")
public class MessageTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "message")
    private String message;
}
