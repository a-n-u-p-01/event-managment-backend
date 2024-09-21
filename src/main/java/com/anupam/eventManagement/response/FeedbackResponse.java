package com.anupam.eventManagement.response;

import com.anupam.eventManagement.request.FeedbackDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedbackResponse {
    private int responseStatus;
    private String responseMessage;
    private FeedbackDTO feedback;
    private List<FeedbackDTO> feedbackDTOList;
}
