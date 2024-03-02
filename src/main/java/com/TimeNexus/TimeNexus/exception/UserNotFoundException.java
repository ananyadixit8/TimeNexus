package com.TimeNexus.TimeNexus.exception;


/**
 * Class for User not found exception object.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructor method
     * @param msg Exception message
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }

}
