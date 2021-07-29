package com.gaggle.assignment.service;

import com.gaggle.assignment.dao.UserDAO;
import com.gaggle.assignment.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for interacting with Users.
 */
@Service
public class UserService {
    private UserDAO dao;

    /**
     * Allow the UserDAO to be injected in.
     * @param dao the data access object used to interact with the data source
     */
    @Autowired
    public void setUserDAO(final UserDAO dao) {
        this.dao = dao;
    }

    /**
     * Returns a User represented by the id provided.
     * @param id the unique id of the User to find
     * @return a User represented by the id provided
     * @throws UserServiceException if an issue occurs retrieving the User
     */
    public User getById(final Integer id) throws UserServiceException {
        try {
            return dao.getById(id);
        } catch (final Exception e) {
            throw new UserServiceException("An issue occurred retrieving a User by id.", e);
        }
    }

    /**
     * Return all users that match the name provided.
     * @param name the name of the Users to search for
     * @return all users that match the name provided
     * @throws UserServiceException if an issue occurs retrieving the User
     */
    public List<User> getByName(final String name) throws UserServiceException {
        try {
            return dao.getByName(name);
        } catch (final Exception e) {
            throw new UserServiceException("An issue occurred retrieving a User by name.", e);
        }
    }
}
