package com.gaggle.assignment.dao;

import com.gaggle.assignment.domain.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the UserDAO class.
 */
class UserDAOTest {
    private static UserDAO dao;

    @BeforeAll
    static void setup() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql")
                .build();
        dao = new UserDAO();
        dao.setDataSource(dataSource);
    }

    @Test
    void whenInjectInMemoryDataSource_thenReturnCorrectUserById() {
        User user = dao.getById(1);

        assertEquals(1, user.getId());
        assertEquals("Bruce", user.getFirstName());
        assertEquals("Wayne", user.getLastName());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Bruce Wayne", "bru", "way", "ce wa", "Bruce", "Wayne", "BrUcEwAyNe" })
    void whenInjectInMemoryDataSource_thenReturnCorrectUsersByName(final String name) {
        List<User> users = dao.getByName(name);
        assertNotNull(users);
        assertEquals(1, users.size());
    }
}
