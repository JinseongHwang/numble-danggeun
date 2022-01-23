package it.numble.numbledanggeun.infrastructure.error.handler;

import it.numble.numbledanggeun.domain.shared.ResponseFormat;
import it.numble.numbledanggeun.infrastructure.error.exception.BusinessLogicException;
import it.numble.numbledanggeun.infrastructure.error.exception.UserDefineException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtSubjectNotAccessTokenException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtSubjectNotRefreshTokenException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtTokenExpiredException;
import it.numble.numbledanggeun.infrastructure.error.exception.jwt.JwtTokenInvalidException;
import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ========== JWT Exception Handler ==========
     */
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ResponseFormat<Void>> handleJwtTokenExpiredException(JwtTokenExpiredException e) {
        log.warn("{}", e.getErrorCode());
        return new ResponseEntity<>(
            ResponseFormat.expire(),
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<ResponseFormat<Void>> handleJwtTokenInvalidException(JwtTokenInvalidException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.INVALID_TOKEN.toString()),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JwtSubjectNotAccessTokenException.class)
    public ResponseEntity<ResponseFormat<Void>> handleJwtSubjectNotAccessTokenException(JwtSubjectNotAccessTokenException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.INVALID_TOKEN.toString()),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JwtSubjectNotRefreshTokenException.class)
    public ResponseEntity<ResponseFormat<Void>> handleJwtSubjectNotRefreshTokenException(JwtSubjectNotRefreshTokenException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.INVALID_TOKEN.toString()),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * ========== General Exception Handler ==========
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseFormat<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
            .getAllErrors()
            .get(0)
            .getDefaultMessage();
        log.warn(errorMessage);
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.INVALID_INPUT_VALUE, errorMessage),
            HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.getStatusCode())
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseFormat<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.INVALID_TYPE_VALUE, e.getMessage()),
            HttpStatus.valueOf(ErrorCode.INVALID_TYPE_VALUE.getStatusCode())
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseFormat<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage()),
            HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ResponseFormat<Void>> handleRuntimeException(BusinessLogicException e) {
        log.warn(e.getMessage());
        final ErrorCode errorCode = e.getErrorCode();
        if (errorCode == null) {
            return new ResponseEntity<>(
                ResponseFormat.fail(e.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
            ResponseFormat.fail(errorCode, e.getMessage()),
            HttpStatus.valueOf(errorCode.getStatusCode())
        );
    }

    @ExceptionHandler(UserDefineException.class)
    public ResponseEntity<ResponseFormat<Void>> handleUserDefineException(UserDefineException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(e.getMessage()),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseFormat<Void>> handleAuthenticationException(AuthenticationException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.UNAUTHENTICATED_USER, e.getMessage()),
            HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * ========== Server Error Handler ==========
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseFormat<Void>> handleIllegalStateException(IllegalStateException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.SERVER_ERROR, e.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseFormat<Void>> handleException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
            ResponseFormat.fail(ErrorCode.SERVER_ERROR, e.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
