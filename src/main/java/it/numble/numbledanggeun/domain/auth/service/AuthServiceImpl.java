package it.numble.numbledanggeun.domain.auth.service;

import it.numble.numbledanggeun.domain.auth.model.AuthDto;
import it.numble.numbledanggeun.infrastructure.error.exception.BusinessLogicException;
import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;
import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import it.numble.numbledanggeun.infrastructure.persistence.repository.user.UserRepository;
import it.numble.numbledanggeun.infrastructure.security.jwt.PayloadCustom;
import it.numble.numbledanggeun.infrastructure.security.jwt.TokenProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public AuthDto.TOKEN signIn(AuthDto.SIGN_IN authSignInDto) {
        final Optional<UserEntity> userEntityOptional = userRepository.fetchOneByEmail(authSignInDto.getEmail());
        if (userEntityOptional.isEmpty()) {
            throw new BusinessLogicException(ErrorCode.BAD_EMAIL);
        }

        final UserEntity userEntity = userEntityOptional.get();
        if (passwordEncoder.matches(authSignInDto.getPassword(), userEntity.getPassword()) == false) {
            throw new BusinessLogicException(ErrorCode.BAD_PASSWORD);
        }

        return tokenProvider.generateTokenPair(
            PayloadCustom.builder()
                .email(userEntity.getEmail())
                .role(userEntity.getRole().toString())
                .build(),
            userEntity.getRefreshToken()
        );
    }
}
