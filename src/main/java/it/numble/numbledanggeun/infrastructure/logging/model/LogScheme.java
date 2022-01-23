package it.numble.numbledanggeun.infrastructure.logging.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.numble.numbledanggeun.domain.shared.ObjectMapperUtil;
import it.numble.numbledanggeun.domain.shared.ResponseCode;
import it.numble.numbledanggeun.domain.shared.ResponseFormat;
import it.numble.numbledanggeun.domain.shared.ServletExtractor.Servlets;
import it.numble.numbledanggeun.infrastructure.interceptor.UserThreadLocal;
import it.numble.numbledanggeun.infrastructure.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Getter
public final class LogScheme {

    private String type;
    private String email;
    private String user_role;
    private String uri;
    private String http_method;
    private String response_code;
    private String response_description;
    private String method_name;

    public LogScheme(LogType type, Servlets servlets, ResponseFormat<?> response, String methodName) {
        final UserEntity userEntity = UserThreadLocal.get();

        if (userEntity != null) {
            this.email = userEntity.getEmail();
            this.user_role = userEntity.getRole().toString();
        }

        this.type = type.toString();
        this.uri = servlets.getRequest().getRequestURI();
        this.http_method = servlets.getRequest().getMethod();
        this.response_code = ResponseCode.of(response.getCode()).toString();
        this.response_description = response.getDescription();
        this.method_name = methodName;
    }

    @Override
    public String toString() {
        try {
            return ObjectMapperUtil.getInstance().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "LogScheme Json Error";
        }
    }
}

