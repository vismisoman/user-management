package com.usermanagment.exception;

public class UserNotFoundException  extends Exception {

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
