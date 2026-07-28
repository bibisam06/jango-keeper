package com.bibi.jangokeeper.domain.account.dto;

import com.bibi.jangokeeper.domain.account.Account;
import java.time.LocalDateTime;

public record AccountResponse(Long accountId, Long userId, Long balance, LocalDateTime updatedAt) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(), account.getUserId(), account.getBalance(), account.getUpdatedAt());
    }
}
