package it.numble.numbledanggeun.infrastructure.error.exception;

import it.numble.numbledanggeun.infrastructure.error.model.ErrorCode;

public class UnauthenticatedException extends BusinessLogicException {

    public UnauthenticatedException() {
        super(ErrorCode.UNAUTHENTICATED_USER);
    }
}
