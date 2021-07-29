package com.gaggle.assignment.service;

/**
 * Thrown when an issue occurs handling users.
 */
public class UserServiceException extends Exception {
    /**
     * Constructs a new exception with a cause and a specified detail message.
     * @param message the specified detail message of this issue
     * @param cause the underlying issue causing this issue
     */
    public UserServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
