package com.bibi.jangokeeper.common.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(Long userId, long currentBalance, long requestedAmount) {
        super("잔액이 부족합니다. userId=" + userId
                + ", currentBalance=" + currentBalance
                + ", requestedAmount=" + requestedAmount);
    }
}
