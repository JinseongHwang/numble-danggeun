package it.numble.numbledanggeun.infrastructure.error.exception;

public class NotFoundException extends BusinessLogicException {

    public NotFoundException(String domain) {
        super(String.format("Cannot find %s", domain));
    }
}
