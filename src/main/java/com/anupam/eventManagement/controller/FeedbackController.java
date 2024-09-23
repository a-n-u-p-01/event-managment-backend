package com.anupam.eventManagement.controller;

import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.request.FeedbackMessageDTO;
import com.anupam.eventManagement.response.FeedbackResponse;
import com.anupam.eventManagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit/{eventId}")
    public ResponseEntity<FeedbackResponse> submitFeedback(@RequestBody FeedbackMessageDTO feedbackMessageDTO, @PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        FeedbackResponse response = feedbackService.submitFeedback(Long.valueOf(currentUser.getId()),eventId, feedbackMessageDTO );
        return ResponseEntity.status(response.getResponseStatus()).body(response);
    }

    @GetMapping("/feed/{eventId}")
    public ResponseEntity<FeedbackResponse> getFeedbacksByEventId(@PathVariable Long eventId) {
        FeedbackResponse feedbacks = feedbackService.getFeedbacksByEventId(eventId);
        return ResponseEntity.status(feedbacks.getResponseStatus()).body(feedbacks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<FeedbackResponse> getFeedbacksByUserId(@PathVariable Long userId) {
        FeedbackResponse feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return ResponseEntity.status(feedbacks.getResponseStatus()).body(feedbacks);
    }
}
