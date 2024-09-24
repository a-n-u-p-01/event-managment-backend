package com.anupam.eventManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long eventId;

    private String title;
    private  String description;
    private String location;
    private LocalDateTime startTime;
    private  LocalDateTime endTime;
    private Integer capacity;
    private Boolean status;
    private String imageUrl;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer", nullable = false)
    private User organizer;

    @OneToOne(targetEntity = TicketPricing.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pricing")
    private TicketPricing ticketPricing ;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

}
