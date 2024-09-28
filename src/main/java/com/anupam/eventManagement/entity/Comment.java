package com.anupam.eventManagement.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key
    private String comment;
    private String userName;
    private Long eventId;
    private String createdAt;
}
