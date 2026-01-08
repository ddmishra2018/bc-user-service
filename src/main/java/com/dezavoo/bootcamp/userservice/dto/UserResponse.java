package com.dezavoo.bootcamp.userservice.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        String name,
        String emailId,
        String courseOpted,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
