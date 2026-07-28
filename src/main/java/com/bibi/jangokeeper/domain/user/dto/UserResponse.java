package com.bibi.jangokeeper.domain.user.dto;

import com.bibi.jangokeeper.domain.user.User;
import java.time.LocalDateTime;

public record UserResponse(Long userId, String email, String name, LocalDateTime createdAt) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt());
    }
}
