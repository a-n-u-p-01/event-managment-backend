package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatMessage,Long> {
}
