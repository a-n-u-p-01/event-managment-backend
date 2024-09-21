package com.anupam.eventManagement.service;

import com.anupam.eventManagement.request.FeedbackMessageDTO;
import com.anupam.eventManagement.response.FeedbackResponse;

public interface FeedbackService {

    FeedbackResponse submitFeedback(Long userId, Long eventId, FeedbackMessageDTO feedbackMessageDTO);
    FeedbackResponse getFeedbacksByEventId(Long eventId);
    FeedbackResponse getFeedbacksByUserId(Long userId);


    }
