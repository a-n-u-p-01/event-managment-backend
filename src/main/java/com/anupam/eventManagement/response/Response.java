package com.anupam.eventManagement.response;

import com.anupam.eventManagement.entity.Event;
import com.anupam.eventManagement.request.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Response {
    private Long id;
    private int statusCode;

    private String message;

    private String token;
    private UserDTO user;
    private List<Event> eventlist;

}
