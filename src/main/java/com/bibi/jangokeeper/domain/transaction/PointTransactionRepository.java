package com.bibi.jangokeeper.domain.transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

    Optional<PointTransaction> findByIdempotencyKey(UUID idempotencyKey);

    List<PointTransaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
