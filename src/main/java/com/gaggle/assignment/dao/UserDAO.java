package com.gaggle.assignment.dao;

import com.gaggle.assignment.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

/**
 * Data source access for users.
 */
@Component
public class UserDAO {
    private static final String SELECT_BY_ID =
            "SELECT id, firstName, lastName FROM user WHERE id = ?";

    private static final String SELECT_BY_NAME =
            "SELECT id, firstName, lastName FROM user WHERE " +
                    "LOWER(firstName) LIKE ? OR " +
                    "LOWER(lastName) LIKE ? OR " +
                    "LOWER(CONCAT_WS(' ', firstName, lastName)) LIKE ? OR " +
                    "LOWER(CONCAT(firstName, lastName)) LIKE ?";

    private JdbcTemplate jdbcTemplate;

    /**
     * Sets the data source this repository should use.
     * @param dataSource the data source to interact with the database with
     */
    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Returns a User represented by the id provided.
     * @param id the unique id of the User to find
     * @return a User represented by the id provided
     */
    public User getById(final int id) {
        return jdbcTemplate.queryForObject(
                SELECT_BY_ID,
                new Object[] { id },
                new UserRowMapper());
    }

    /**
     * Return all users that match the name provided.
     * @param name the name of the Users to search for
     * @return all users that match the name provided
     */
    public List<User> getByName(final String name) {
        String lowerName = name.toLowerCase();
        String likeFirstName = String.format("%s%%", lowerName);
        String likeLastName = String.format("%%%s", lowerName);
        String likeFullName = String.format("%%%s%%", lowerName);
        return jdbcTemplate.query(
                SELECT_BY_NAME,
                new Object[] { likeFirstName, likeLastName, likeFullName, likeFullName },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR },
                new UserRowMapper());
    }
}
