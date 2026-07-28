package com.bibi.jangokeeper.domain.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record PointRequest(
        @NotNull(message = "userId는 필수입니다.") Long userId,
        @NotNull(message = "금액은 필수입니다.") @Positive(message = "금액은 0보다 커야 합니다.") Long amount,
        @NotNull(message = "idempotencyKey는 필수입니다.") UUID idempotencyKey) {}
