package it.numble.numbledanggeun.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import it.numble.numbledanggeun.domain.auth.model.AuthDto;
import it.numble.numbledanggeun.domain.user.model.UserRole;
import it.numble.numbledanggeun.infrastructure.error.exception.NotFoundException;
import it.numble.numbledanggeun.infrastructure.error.exception.UnauthenticatedException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtTokenExpiredException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtTokenInvalidException;
import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import it.numble.numbledanggeun.infrastructure.persistence.repository.user.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider implements TokenProvider {

    private final UserRepository userRepository;

    @Value("${global.jwt.secret}")
    private String SECRET_KEY;

    private static final Long ACCESS_EXPIRE = 1_000L * 60 * 30; // 30 minutes
    private static final Long REFRESH_EXPIRE = 1_000L * 60 * 60 * 24 * 14; // 2 weeks

    private static final String[] TOKEN_HEADER = {"typ", "JWT"};
    private static final String ACCESS_SUBJECT = "AccessToken";
    private static final String REFRESH_SUBJECT = "RefreshToken";

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    @Override
    public UserEntity findUserByToken(String token) {
        return userRepository.fetchOneByEmail(getEmailByToken(token))
            .orElseThrow(() -> new NotFoundException("UserEntity"));
    }

    @Override
    public Optional<String> resolveToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    @Override
    public String createAccessToken(PayloadCustom payload) {
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + ACCESS_EXPIRE);
        return Jwts.builder()
            .setHeaderParam(TOKEN_HEADER[0], TOKEN_HEADER[1])
            .setClaims(generateClaims(payload))
            .setIssuedAt(issueDate)
            .setSubject(ACCESS_SUBJECT)
            .setExpiration(expireDate) // 유효기간 30분
            .signWith(SignatureAlgorithm.HS256, generateSigningKey())
            .compact();
    }

    @Override
    public String createAccessToken(String refreshToken) {
        UserEntity userEntity = findUserByToken(refreshToken);

        if (!userEntity.getRefreshToken().equals(refreshToken)) {
            throw new UnauthenticatedException();
        }

        return createAccessToken(PayloadCustom.builder()
            .email(userEntity.getEmail())
            .role(userEntity.getRole().toString())
            .build());
    }

    @Override
    public String createRefreshToken(PayloadCustom payload) {
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + REFRESH_EXPIRE);
        return Jwts.builder()
            .setHeaderParam(TOKEN_HEADER[0], TOKEN_HEADER[1])
            .setClaims(generateClaims(payload))
            .setIssuedAt(issueDate)
            .setSubject(REFRESH_SUBJECT)
            .setExpiration(expireDate) // 유효기간 2주
            .signWith(SignatureAlgorithm.HS256, generateSigningKey())
            .compact();
    }

    @Override
    public AuthDto.TOKEN generateTokenPair(PayloadCustom payload) {
        return AuthDto.TOKEN.builder()
            .accessToken(createAccessToken(payload))
            .refreshToken(createRefreshToken(payload))
            .build();
    }

    @Override
    public AuthDto.TOKEN generateTokenPair(PayloadCustom payload, String refreshToken) {
        return AuthDto.TOKEN.builder()
            .accessToken(createAccessToken(payload))
            .refreshToken(refreshToken)
            .build();
    }

    @Override
    public AuthDto.TOKEN generateTokenPairByUser(UserEntity user) {
        return generateTokenPair(
            PayloadCustom.builder()
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build()
        );
    }

    @Override
    public AuthDto.TOKEN generateEmptyTokenPair() {
        return new AuthDto.TOKEN();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(generateSigningKey())
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature");
            throw new JwtTokenInvalidException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new JwtTokenInvalidException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
            throw new JwtTokenExpiredException();
        } catch (IllegalArgumentException e) {
            log.error("Empty JWT claims");
            throw new JwtTokenInvalidException("Empty JWT claims");
        }
    }

    @Override
    public boolean isAccessToken(String token) {
        return getTypeByToken(token).equals(ACCESS_SUBJECT);
    }

    @Override
    public boolean isRefreshToken(String token) {
        return getTypeByToken(token).equals(REFRESH_SUBJECT);
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = new User(
            getEmailByToken(token),
            "",
            getAuthorities(getRoleByToken(token))
        );

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Set<? extends GrantedAuthority> getAuthorities(UserRole role) {
        Set<GrantedAuthority> set = new HashSet<>();

        if (role.equals(UserRole.ROLE_ADMIN)) {
            set.add(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.toString()));
        }
        set.add(new SimpleGrantedAuthority(UserRole.ROLE_USER.toString()));

        return set;
    }

    private byte[] generateSigningKey() {
        return SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    }

    private Claims generateClaims(PayloadCustom payload) {
        Claims claims = Jwts.claims();
        claims.put("email", payload.getEmail());
        claims.put("role", payload.getRole());
        return claims;
    }

    private String getEmailByToken(String token) {
        return (String) Jwts.parser()
            .setSigningKey(generateSigningKey())
            .parseClaimsJws(token)
            .getBody()
            .get("email");
    }

    private UserRole getRoleByToken(String token) {
        final String role = (String) Jwts.parser()
            .setSigningKey(generateSigningKey())
            .parseClaimsJws(token)
            .getBody()
            .get("role");
        return UserRole.of(role);
    }

    private String getTypeByToken(String token) {
        return Jwts.parser()
            .setSigningKey(generateSigningKey())
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
