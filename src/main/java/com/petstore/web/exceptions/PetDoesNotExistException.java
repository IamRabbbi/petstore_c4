package com.petstore.web.exceptions;

public class PetDoesNotExistException extends Exception{

    public PetDoesNotExistException() {
        super();
    }

    public PetDoesNotExistException(String message) {
        super(message);
    }

    public PetDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected PetDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
