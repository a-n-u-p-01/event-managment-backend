package com.anupam.eventManagement.response;

import com.anupam.eventManagement.request.UserFeedbackDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserFeedbackResponse {
    private int responseStatus;
    private String responseMessage;
    private UserFeedbackDTO feedback;
    private List<UserFeedbackDTO> feedbackDTOList;
}
