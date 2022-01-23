package it.numble.numbledanggeun.infrastructure.error.exception.jwt;

import it.numble.numbledanggeun.infrastructure.error.exception.UserDefineException;

public class JwtSubjectNotRefreshTokenException extends UserDefineException {

    public JwtSubjectNotRefreshTokenException(String message) {
        super(message);
    }
}
