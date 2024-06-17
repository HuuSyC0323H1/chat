package com.person.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "calls")
@Data
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long callId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "call_type")
    private String callType;

    @Column(name = "status")
    private Integer status;
}
