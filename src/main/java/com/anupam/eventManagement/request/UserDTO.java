package com.anupam.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String fullName;
    private String email;
    private Date createdAt;
    private String authProvider;
    private String password;
}
