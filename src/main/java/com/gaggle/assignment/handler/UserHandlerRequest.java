package com.gaggle.assignment.handler;

import java.io.Serializable;

/**
 * Represents the incoming JSON request.
 */
public class UserHandlerRequest implements Serializable {
    private static final long serialVersionUID = 8155119925029019312L;

    /**
     * The operation to be performed.
     */
    public UserOperation operation;

    /**
     * The user identifier to search with.
     */
    public Integer id;

    /**
     * The user name to search with.
     */
    public String name;
}
