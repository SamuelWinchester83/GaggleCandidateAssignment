package com.gaggle.assignment.handler;

import com.gaggle.assignment.domain.User;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an error to be returned when an issue occurs.
 */
public class UserHandlerResponse implements Serializable {
    private static final long serialVersionUID = 9057251087589727438L;

    /**
     * The results of the search.
     */
    public List<User> users;

    /**
     * The cause of the error.
     */
    public String cause;

    /**
     * A user-friendly explanation of the error.
     */
    public String message;
}
