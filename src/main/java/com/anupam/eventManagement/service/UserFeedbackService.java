package com.anupam.eventManagement.service;

import com.anupam.eventManagement.request.FeedbackMessageDTO;
import com.anupam.eventManagement.response.UserFeedbackResponse;

public interface UserFeedbackService {
    UserFeedbackResponse createFeedback(Long userId, Long eventId ,FeedbackMessageDTO feedbackDTO);
    UserFeedbackResponse getFeedbackByEventId(Long eventId);
}
