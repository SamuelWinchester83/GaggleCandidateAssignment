package com.gaggle.assignment.dao;

import com.gaggle.assignment.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    /**
     * {@inheritdoc}
     */
    @Override
    public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");

        return new User(id, firstName, lastName);
    }
}
