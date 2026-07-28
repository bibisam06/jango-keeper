package com.bibi.jangokeeper.domain.account;

import com.bibi.jangokeeper.common.exception.AccountNotFoundException;
import com.bibi.jangokeeper.domain.account.dto.AccountResponse;
import com.bibi.jangokeeper.domain.account.dto.PointRequest;
import com.bibi.jangokeeper.domain.transaction.PointTransaction;
import com.bibi.jangokeeper.domain.transaction.PointTransactionRepository;
import com.bibi.jangokeeper.domain.transaction.TransactionType;
import com.bibi.jangokeeper.domain.transaction.dto.TransactionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public TransactionResponse charge(PointRequest request) {
        return process(request, TransactionType.CHARGE);
    }

    @Transactional
    public TransactionResponse use(PointRequest request) {
        return process(request, TransactionType.USE);
    }

    private TransactionResponse process(PointRequest request, TransactionType type) {
        // 같은 idempotencyKey로 재요청이 오면 재처리하지 않고 이전 결과를 그대로 반환한다.
        // 참고: 동시에 같은 키로 요청이 몰리는 경합 상황은 2~3단계에서 락/유니크 제약으로 보강 예정.
        var existing = pointTransactionRepository.findByIdempotencyKey(request.idempotencyKey());
        if (existing.isPresent()) {
            return TransactionResponse.from(existing.get());
        }

        Account account = accountRepository
                .findByUserId(request.userId())
                .orElseThrow(() -> new AccountNotFoundException(request.userId()));

        if (type == TransactionType.CHARGE) {
            account.charge(request.amount());
        } else {
            account.use(request.amount());
        }

        PointTransaction transaction = pointTransactionRepository.save(new PointTransaction(
                account.getId(),
                account.getUserId(),
                type,
                request.amount(),
                account.getBalance(),
                request.idempotencyKey()));

        return TransactionResponse.from(transaction);
    }

    public AccountResponse getAccount(Long userId) {
        Account account =
                accountRepository.findByUserId(userId).orElseThrow(() -> new AccountNotFoundException(userId));
        return AccountResponse.from(account);
    }

    public List<TransactionResponse> getTransactions(Long userId) {
        Account account =
                accountRepository.findByUserId(userId).orElseThrow(() -> new AccountNotFoundException(userId));
        return pointTransactionRepository.findByAccountIdOrderByCreatedAtDesc(account.getId()).stream()
                .map(TransactionResponse::from)
                .toList();
    }
}
