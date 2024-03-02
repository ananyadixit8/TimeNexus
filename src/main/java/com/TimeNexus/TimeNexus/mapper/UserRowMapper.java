package com.TimeNexus.TimeNexus.mapper;

import com.TimeNexus.TimeNexus.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to map database returned data into User object.
 */
public class UserRowMapper implements RowMapper<User> {

    /**
     * Method to map a single row fetched from the database into the User object.
     * @param rs ResultSet containing data from database
     * @param rowNum Row number
     * @return User object
     * @throws SQLException
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
