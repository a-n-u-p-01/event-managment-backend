package com.anupam.eventManagement.service.impl;


import com.anupam.eventManagement.model.CommunicationModel;
import com.anupam.eventManagement.model.Event;
import com.anupam.eventManagement.model.User;
import com.anupam.eventManagement.repository.CommunicationRepository;
import com.anupam.eventManagement.repository.EventRepository;
import com.anupam.eventManagement.repository.UserRepository;
import com.anupam.eventManagement.request.CommunicationDTO;
import com.anupam.eventManagement.request.FeedbackMessageDTO;
import com.anupam.eventManagement.response.CommunicationResponse;
import com.anupam.eventManagement.service.CommunicationService;
import com.anupam.eventManagement.exception.EventException;
import com.anupam.eventManagement.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public CommunicationResponse sendCommunication(Long eventId, Long senderId, Long recipientId, FeedbackMessageDTO feedbackMessageDTO) {
        CommunicationResponse communicationResponse = new CommunicationResponse();
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EventException("Event not found"));

            User sender = userRepository.findById(senderId)
                    .orElseThrow(() -> new UserException("Sender not found"));

            User recipient = userRepository.findById(recipientId)
                    .orElseThrow(() -> new UserException("Recipient not found"));

            CommunicationModel communication = new CommunicationModel();
            communication.setEvent(event);
            communication.setSender(sender);
            communication.setRecipient(recipient);
            communication.setMessage(feedbackMessageDTO.getMessage());
            communication.setSentAt(LocalDateTime.now());

            communicationRepository.save(communication);

            communicationResponse.setStatusCode(201);
            communicationResponse.setMessage("Message sent successfully");
        } catch (EventException | UserException e) {
            communicationResponse.setStatusCode(400);
            communicationResponse.setMessage("Failed to send message: " + e.getMessage());
        } catch (DataAccessException e) {
            communicationResponse.setStatusCode(500);
            communicationResponse.setMessage("Database error: " + e.getMessage());
        } catch (Exception e) {
            communicationResponse.setStatusCode(500);
            communicationResponse.setMessage("An unexpected error occurred: " + e.getMessage());
        }

        return communicationResponse;
    }

    @Override
    public List<CommunicationDTO> getCommunications(Long eventId, Long senderId) {
        try {
            List<CommunicationModel> communications = communicationRepository.findByEventEventIdAndSenderId(eventId, senderId);

            return communications.stream()
                    .map(comm -> new CommunicationDTO(comm.getMessage(), comm.getRecipient().getFullName(), comm.getSentAt(), comm.getEvent().getTitle()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    public List<CommunicationDTO> getCommunicationsByRecipientId(Long recipientId) {
        try {
            List<CommunicationModel> communications = communicationRepository.findByRecipientId(recipientId);

            return communications.stream()
                    .map(comm -> new CommunicationDTO(
                            comm.getMessage(),
                            comm.getSender().getFullName(),
                            comm.getSentAt(), comm.getEvent().getTitle())
                            )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving communications: " + e.getMessage());
        }
    }
}
