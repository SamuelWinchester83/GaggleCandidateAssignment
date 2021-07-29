package com.gaggle.assignment.service;

import com.gaggle.assignment.dao.UserDAO;
import com.gaggle.assignment.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the UserService class.
 */
class UserServiceTest {

    @Test
    void whenDaoSucceeds_thenReturnCorrectUserById() throws Exception {
        User expected = new User(1, "firstName", "lastName");

        UserDAO dao = mock(UserDAO.class);
        when(dao.getById(anyInt())).thenReturn(expected);

        UserService manager = new UserService();
        manager.setUserDAO(dao);
        User actual = manager.getById(1);
        verify(dao).getById(1);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    void whenDaoThrowsExceptionOnGetById_thenThrowUserException() {
        UserDAO dao = mock(UserDAO.class);
        when(dao.getById(anyInt())).thenThrow(new RuntimeException());

        UserService manager = new UserService();
        manager.setUserDAO(dao);

        assertThrows(UserServiceException.class, () -> {
            manager.getById(1);
        });

        verify(dao).getById(1);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Bruce Wayne", "bru", "way", "ce wa", "Bruce", "Wayne" })
    void whenDaoSucceeds_thenReturnCorrectUserByName(final String name) throws Exception {
        User expectedUser = new User(1, "Bruce", "Wayne");
        List<User> expected = new ArrayList<>();
        expected.add(expectedUser);

        UserDAO dao = mock(UserDAO.class);
        when(dao.getByName(anyString())).thenReturn(expected);

        UserService manager = new UserService();
        manager.setUserDAO(dao);
        List<User> actual = manager.getByName(name);
        verify(dao).getByName(name);

        assertEquals(actual.size(), expected.size());

        User actualUser = actual.get(0);
        assertEquals(actualUser.getId(), expectedUser.getId());
        assertEquals(actualUser.getFirstName(), expectedUser.getFirstName());
        assertEquals(actualUser.getLastName(), expectedUser.getLastName());
    }

    @Test
    void whenDaoThrowsExceptionOnGetByName_thenThrowUserException() throws Exception {
        UserDAO dao = mock(UserDAO.class);
        when(dao.getByName(anyString())).thenThrow(new RuntimeException());

        UserService manager = new UserService();
        manager.setUserDAO(dao);

        String name = "bruce";
        assertThrows(UserServiceException.class, () -> {
            manager.getByName(name);
        });

        verify(dao).getByName(name);
    }
}
