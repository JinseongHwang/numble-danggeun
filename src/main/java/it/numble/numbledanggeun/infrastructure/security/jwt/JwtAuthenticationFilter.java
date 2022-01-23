package it.numble.numbledanggeun.infrastructure.security.jwt;

import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtSubjectNotAccessTokenException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtSubjectNotRefreshTokenException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtTokenExpiredException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtTokenInvalidException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        final Optional<String> tokenOptional = tokenProvider.resolveToken(request);

        try {
            if (tokenOptional.isPresent()) {
                final String token = tokenOptional.get();
                tokenValidation(request.getRequestURI(), token);

                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtTokenExpiredException |
            JwtTokenInvalidException |
            JwtSubjectNotAccessTokenException |
            JwtSubjectNotRefreshTokenException exception) {

            handlerExceptionResolver.resolveException(request, response, null, exception);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void tokenValidation(String uri, String token) {
        tokenProvider.isValidToken(token);
        if (uri.equals("/v1/api/auth/refresh") && tokenProvider.isRefreshToken(token) == false) {
            throw new JwtSubjectNotRefreshTokenException("JWT Subject is not RefreshToken");
        }
        if (tokenProvider.isAccessToken(token) == false) {
            throw new JwtSubjectNotAccessTokenException("JWT Subject is not AccessToken");
        }
    }
}
