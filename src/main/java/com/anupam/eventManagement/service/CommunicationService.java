package com.anupam.eventManagement.service;

import com.anupam.eventManagement.request.CommunicationDTO;
import com.anupam.eventManagement.request.FeedbackMessageDTO;
import com.anupam.eventManagement.response.CommunicationResponse;

import java.util.List;

public interface CommunicationService {
        CommunicationResponse sendCommunication(Long eventId, Long senderId, Long recipientId, FeedbackMessageDTO feedbackMessageDTO);
        List<CommunicationDTO> getCommunications(Long eventId, Long senderId);
        List<CommunicationDTO> getCommunicationsByRecipientId(Long recipientId);

}
