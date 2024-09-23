package com.anupam.eventManagement.response;

import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.request.UserDTO;
import lombok.Data;
import lombok.extern.java.Log;

@Data
public class UserDataResponse {
    private UserDTO userDTO;
    private Long eventHosted;
    private Long eventBooked;
}
