package it.numble.numbledanggeun.domain.shared;

import io.swagger.annotations.ApiModelProperty;
import it.numble.numbledanggeun.infrastructure.error.exception.UserDefineException;
import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class ResponseFormat<T> {

    @ApiModelProperty(example = "성공은 1 or 실패는 2 or 토큰 만료는 3")
    private Integer code;

    @ApiModelProperty(example = "true")
    private Boolean result;

    @ApiModelProperty(example = "object type response body")
    private T data;

    @ApiModelProperty(example = "성공 or 에러 메세지 or 토큰이 만료되었습니다.")
    private String description;

    public static ResponseFormat<Void> ok() {
        return ResponseFormat.<Void>builder()
            .code(ResponseCode.SUCCESS.getCode())
            .result(true)
            .data(null)
            .description("성공")
            .build();
    }

    public static <T> ResponseFormat<T> ok(T data) {
        return ResponseFormat.<T>builder()
            .code(ResponseCode.SUCCESS.getCode())
            .result(true)
            .data(data)
            .description("성공")
            .build();
    }

    public static ResponseFormat<Void> fail(String message) {
        return ResponseFormat.<Void>builder()
            .code(ResponseCode.FAIL.getCode())
            .result(false)
            .data(null)
            .description(message)
            .build();
    }

    public static ResponseFormat<Void> fail(ErrorCode errorCode, String originalErrorMessage) {
        return ResponseFormat.<Void>builder()
            .code(ResponseCode.FAIL.getCode())
            .result(false)
            .data(null)
            .description(errorCode.toString() + " : " + originalErrorMessage)
            .build();
    }

    public static ResponseFormat<Void> expire() {
        return ResponseFormat.<Void>builder()
            .code(ResponseCode.TOKEN_EXPIRED.getCode())
            .result(false)
            .data(null)
            .description(ErrorCode.EXPIRED_TOKEN.toString())
            .build();
    }

    // for exception handler, logging
    public static ResponseFormat<?> of(Object response) {
        if (response instanceof ResponseFormat) {
            return (ResponseFormat<?>) response;
        }
        try {
            if (response instanceof ResponseEntity) {
                response = ((ResponseEntity<?>) response).getBody();
            }
            return (ResponseFormat<?>) response;
        } catch (Exception e) {
            throw new UserDefineException("ResponseFormat 형변환 과정에서 문제가 발생했습니다. = {}", e.getMessage());
        }
    }
}
