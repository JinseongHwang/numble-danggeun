package it.numble.numbledanggeun.infrastructure.interceptor;

import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import it.numble.numbledanggeun.infrastructure.security.jwt.TokenProvider;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Optional<String> tokenOptional = tokenProvider.resolveToken(request);

        // 로그인 또는 회원가입 시
        if (tokenOptional.isEmpty()) {
            return true;
        }

        final UserEntity userEntity = tokenProvider.findUserByToken(tokenOptional.get());
        UserThreadLocal.set(userEntity);
        log.debug("Set threadLocal | email = {}", userEntity.getEmail());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (UserThreadLocal.isPresent()) {
            final UserEntity userEntity = UserThreadLocal.get();
            log.debug("Remove threadLocal | email = {}", userEntity.getEmail());
            UserThreadLocal.remove();
        }
    }
}
