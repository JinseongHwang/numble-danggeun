package it.numble.numbledanggeun.infrastructure.error.exception;

import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;

public class UnauthorizedException extends BusinessLogicException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED_USER);
    }
}
