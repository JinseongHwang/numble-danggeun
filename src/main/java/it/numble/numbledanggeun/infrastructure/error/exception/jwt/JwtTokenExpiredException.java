package it.numble.numbledanggeun.infrastructure.error.exception.jwt;

import it.numble.numbledanggeun.infrastructure.error.exception.BusinessLogicException;
import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException {

    public JwtTokenExpiredException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
