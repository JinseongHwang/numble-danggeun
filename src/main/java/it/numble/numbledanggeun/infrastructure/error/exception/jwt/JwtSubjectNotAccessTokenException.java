package it.numble.numbledanggeun.infrastructure.error.exception.jwt;

import it.numble.numbledanggeun.infrastructure.error.exception.UserDefineException;

public class JwtSubjectNotAccessTokenException extends UserDefineException {

    public JwtSubjectNotAccessTokenException(String message) {
        super(message);
    }
}
