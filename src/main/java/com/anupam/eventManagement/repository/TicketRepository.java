package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventEventId(Long eventId);
    List<Ticket> findByUserId(Long userId);

    void deleteAllByEventEventId(Long eventId);
}
