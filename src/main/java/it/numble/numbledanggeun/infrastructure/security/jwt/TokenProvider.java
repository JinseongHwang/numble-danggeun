package it.numble.numbledanggeun.infrastructure.security.jwt;

import it.numble.numbledanggeun.domain.auth.model.AuthDto;
import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    UserEntity findUserByToken(String token);

    Optional<String> resolveToken(HttpServletRequest request);

    String createAccessToken(PayloadCustom payload);

    String createAccessToken(String refreshToken);

    String createRefreshToken(PayloadCustom payload);

    AuthDto.TOKEN generateTokenPair(PayloadCustom payload);

    AuthDto.TOKEN generateTokenPair(PayloadCustom payload, String refreshToken);

    AuthDto.TOKEN generateTokenPairByUser(UserEntity user);

    AuthDto.TOKEN generateEmptyTokenPair();

    boolean isValidToken(String token);

    boolean isAccessToken(String token);

    boolean isRefreshToken(String token);

    Authentication getAuthentication(String token);
}
