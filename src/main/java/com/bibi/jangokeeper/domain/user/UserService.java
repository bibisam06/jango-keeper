package com.bibi.jangokeeper.domain.user;

import com.bibi.jangokeeper.common.exception.DuplicateEmailException;
import com.bibi.jangokeeper.common.exception.UserNotFoundException;
import com.bibi.jangokeeper.domain.account.Account;
import com.bibi.jangokeeper.domain.account.AccountRepository;
import com.bibi.jangokeeper.domain.user.dto.SignUpRequest;
import com.bibi.jangokeeper.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public UserResponse signUp(SignUpRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new DuplicateEmailException(request.email());
        });

        User user = userRepository.save(new User(request.email(), request.name()));
        // 가입과 동시에 포인트 계좌를 하나 만들어 둔다 (1 user : 1 account)
        accountRepository.save(new Account(user.getId()));

        return UserResponse.from(user);
    }

    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return UserResponse.from(user);
    }
}
