package com.empresa.api.infrastructure.web.rest.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.web.rest.UserRequest;
import com.empresa.api.infrastructure.web.rest.UserResponse;

public class UserMapper {

    private UserMapper() {}

    public static User toUser(UserRequest request) {
        return User.builder()
            .nombres(request.getNombres())
            .apellidos(request.getApellidos())
            .email(request.getEmail())
            .edad(request.getEdad())
            .build();
    }

    public static UserResponse toResponse(String message, User user) {
        return UserResponse.builder()
            .status("SUCCESS")
            .message(message)
            .data(user)
            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .build();
    }

    public static UserResponse toListResponse(String message, List<User> users) {
        return UserResponse.builder()
            .status("SUCCESS")
            .message(message)
            .data(users)
            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .build();
    }

    public static UserResponse toErrorResponse(String message, String error) {
        return UserResponse.builder()
            .status("ERROR")
            .message(message)
            .error(error)
            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .build();
    }
}
