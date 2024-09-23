package com.anupam.eventManagement.service;

import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.request.LoginRequest;
import com.anupam.eventManagement.request.UserDTO;
import com.anupam.eventManagement.response.Response;
import com.anupam.eventManagement.response.UserDataResponse;

import java.util.List;

public interface UserService {
    Response register(UserDTO user);

    Response login(LoginRequest loginRequest);

    List<User> getAllUsers();

    Response getUserById(Long userId);
    Response deleteUser(Long userId);
    Response getInfo(String email);

//    Response getAllUserEvents(Long userId);


    User updateUser(Long id, User userDetails);


    UserDataResponse getUserData(User currentUser);
}
