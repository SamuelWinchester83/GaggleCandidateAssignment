package com.gaggle.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testNoArgConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
    }

    @Test
    void testArgConstructors() {
        User user = new User(1, "firstName", "lastName");
        assertEquals(1, user.getId());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());


        User user2 = new User("firstName", "lastName");
        assertNull(user2.getId());
        assertEquals("firstName", user2.getFirstName());
        assertEquals("lastName", user2.getLastName());
    }
}
