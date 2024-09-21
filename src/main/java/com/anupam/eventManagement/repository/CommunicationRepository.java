package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<Message, Long> {
    List<Message> findByEventEventIdAndSenderId(Long eventId, Long senderId);
    List<Message> findByRecipientId(Long recipientId);

}
