package com.webchat.validate;

public class ValidationException extends Exception {

    public ValidationException() {
        super("Oops! Something Went Wrong");
    }

    public ValidationException(String msg) {
        super(msg);
    }

}
