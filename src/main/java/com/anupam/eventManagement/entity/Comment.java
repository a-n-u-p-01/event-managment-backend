package com.anupam.eventManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key
    private String comment;
    private String userName;
    private Long eventId;
    private LocalDateTime createdAt;
}
