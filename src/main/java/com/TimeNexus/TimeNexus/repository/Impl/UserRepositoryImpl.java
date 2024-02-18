package com.TimeNexus.TimeNexus.repository.Impl;

import com.TimeNexus.TimeNexus.dto.User;
import com.TimeNexus.TimeNexus.mapper.UserRowMapper;
import com.TimeNexus.TimeNexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM user_account",
                new UserRowMapper()
        );
    }

    @Override
    public User findById(Long user_id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM user_account WHERE user_id=?",
                new UserRowMapper(),
                user_id
        );
    }

    @Override
    public User create(User user) {
        jdbcTemplate.update(
                "INSERT INTO user_account VALUES(?,?,?,?,?)",
                new Object[] { user.getUser_id(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword() }
        );
        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(
                "UPDATE user_account SET first_name=?, last_name=?, email=?, password=? WHERE user_id=?",
                new Object[] { user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword(), user.getUser_id() }
        );
        return user;
    }

    @Override
    public int deleteById(Long user_id) {
        return jdbcTemplate.update(
                "DELETE FROM user_account WHERE user_id=?",
                user_id
        );
    }
}