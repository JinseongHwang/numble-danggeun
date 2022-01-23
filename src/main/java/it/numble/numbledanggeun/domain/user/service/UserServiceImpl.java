package it.numble.numbledanggeun.domain.user.service;

import it.numble.numbledanggeun.domain.user.model.UserDto;
import it.numble.numbledanggeun.domain.user.model.UserRole;
import it.numble.numbledanggeun.infrastructure.error.exception.BusinessLogicException;
import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;
import it.numble.numbledanggeun.infrastructure.interceptor.UserThreadLocal;
import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import it.numble.numbledanggeun.infrastructure.persistence.repository.user.UserRepository;
import it.numble.numbledanggeun.infrastructure.security.jwt.PayloadCustom;
import it.numble.numbledanggeun.infrastructure.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public boolean signUp(UserDto.CREATE userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new BusinessLogicException(ErrorCode.DUPLICATED_USER);
        }

        final String refreshToken = tokenProvider.createRefreshToken(
            PayloadCustom.builder()
                .email(userCreateDto.getEmail())
                .role(UserRole.ROLE_USER.toString())
                .build()
        );

        userRepository.save(
            UserEntity.builder()
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .username(userCreateDto.getUsername())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .nickname(userCreateDto.getNickname())
                .refreshToken(refreshToken)
                .build()
        );
        return true;
    }

    @Override
    public UserDto.READ readUserInfo() {
        final UserEntity userEntity = UserThreadLocal.get();
        return UserDto.READ.builder()
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .build();
    }
}
