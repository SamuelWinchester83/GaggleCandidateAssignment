package com.gaggle.assignment.domain;

import java.io.Serializable;

/**
 * Represents a user in the system.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1834934748026016650L;

    private Integer id;
    private String firstName;
    private String lastName;

    /**
     * Default empty constructor
     */
    public User() {

    }

    /**
     * Constructor taking in all properties needing to create a full User.
     * @param id a unique identifier for the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     */
    public User(final Integer id, final String firstName, final String lastName) {
        this();
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * Constructor taking in first and last name properties needing to create a User.
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     */
    public User(final String firstName, final String lastName) {
        this(null, firstName, lastName);
    }

    /**
     * Returns the unique identifier for this User.
     * @return the unique identifier for this User
     */
    public Integer getId() {
        return id;
    }

    private void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns the first name of this User.
     * @return the first name of this User
     */
    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of this User.
     * @return the last name of this User
     */
    public String getLastName() {
        return lastName;
    }

    private void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}
