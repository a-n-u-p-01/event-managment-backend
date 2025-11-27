package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findAllByEventId(Long eventId);
}
