package it.numble.numbledanggeun.infrastructure.error.exception.jwt;

import it.numble.numbledanggeun.infrastructure.error.exception.UserDefineException;

public class JwtTokenInvalidException extends UserDefineException {

    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
