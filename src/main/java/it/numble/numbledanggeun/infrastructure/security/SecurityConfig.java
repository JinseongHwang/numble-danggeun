package it.numble.numbledanggeun.infrastructure.security;

import it.numble.numbledanggeun.infrastructure.security.jwt.JwtAuthenticationFilter;
import it.numble.numbledanggeun.infrastructure.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AccessDeniedHandlerCustom accessDeniedHandlerCustom;
    private final AuthenticationEntryPointCustom authenticationEntryPointCustom;

    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**",
        "/configuration/ui",
        "/configuration/security",
        "favicon.ico"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests().anyRequest().permitAll()
            .and()
                // ???????????? ????????? ????????? ?????? ???
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerCustom)
            .and()
                // ???????????? ?????? ????????? ???
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointCustom)
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl())
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
