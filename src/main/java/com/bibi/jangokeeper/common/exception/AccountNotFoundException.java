package com.bibi.jangokeeper.common.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long userId) {
        super("계좌를 찾을 수 없습니다. userId=" + userId);
    }
}
