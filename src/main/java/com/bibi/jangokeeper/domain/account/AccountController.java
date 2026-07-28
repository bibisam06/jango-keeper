package com.bibi.jangokeeper.domain.account;

import com.bibi.jangokeeper.domain.account.dto.AccountResponse;
import com.bibi.jangokeeper.domain.account.dto.PointRequest;
import com.bibi.jangokeeper.domain.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/points/charge")
    public ResponseEntity<TransactionResponse> charge(@Valid @RequestBody PointRequest request) {
        return ResponseEntity.ok(accountService.charge(request));
    }

    @PostMapping("/points/use")
    public ResponseEntity<TransactionResponse> use(@Valid @RequestBody PointRequest request) {
        return ResponseEntity.ok(accountService.use(request));
    }

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccount(userId));
    }

    @GetMapping("/accounts/{userId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getTransactions(userId));
    }
}
