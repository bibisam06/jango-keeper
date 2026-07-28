package com.bibi.jangokeeper.domain.transaction.dto;

import com.bibi.jangokeeper.domain.transaction.PointTransaction;
import com.bibi.jangokeeper.domain.transaction.TransactionType;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id, TransactionType type, Long amount, Long balanceAfter, LocalDateTime createdAt) {

    public static TransactionResponse from(PointTransaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getCreatedAt());
    }
}
