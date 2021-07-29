package com.gaggle.assignment.config;

import com.gaggle.assignment.dao.UserDAO;
import com.gaggle.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.gaggle.assignment")
public class SpringConfig {
    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    /**
     * Returns a data source used to interact with a database.
     * @return a data source used to interact with a database
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /**
     * Returns a data access object for interaction with users.
     * @return a data access object for interaction with users
     */
    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

    /**
     * Returns a data access object for interaction with users.
     * @return a data access object for interaction with users
     */
    @Bean
    public UserService getUserService() {
        return new UserService();
    }
}
