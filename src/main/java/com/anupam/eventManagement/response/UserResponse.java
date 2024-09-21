package com.anupam.eventManagement.response;

import com.anupam.eventManagement.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int statusCode;

    private String message;

    private String token;
    private UserDTO user;
}
