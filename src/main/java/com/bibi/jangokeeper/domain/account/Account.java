package com.bibi.jangokeeper.domain.account;

import com.bibi.jangokeeper.common.exception.InsufficientBalanceException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "balance", nullable = false)
    private Long balance;

    // 낙관적 락: 동시 수정 감지용. 2단계 동시성 처리의 기반이 되는 필드.
    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Account(Long userId) {
        this.userId = userId;
        this.balance = 0L;
    }

    public void charge(long amount) {
        validateAmount(amount);
        this.balance += amount;
    }

    public void use(long amount) {
        validateAmount(amount);
        if (this.balance < amount) {
            throw new InsufficientBalanceException(this.userId, this.balance, amount);
        }
        this.balance -= amount;
    }

    private void validateAmount(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }
    }

    @PrePersist
    @PreUpdate
    void onSave() {
        this.updatedAt = LocalDateTime.now();
    }
}
