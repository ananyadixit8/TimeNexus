package com.TimeNexus.TimeNexus.repository.Impl;

import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.mapper.UserRowMapper;
import com.TimeNexus.TimeNexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

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
    public User findById(int userId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM user_account WHERE user_id=?",
                new UserRowMapper(),
                userId
        );
    }

    @Override
    @Transactional
    public User create(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> {
                PreparedStatement pst = connection.prepareStatement("INSERT INTO user_account (first_name, last_name, email, password) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, user.getFirstName());
                pst.setString(2, user.getLast_name());
                pst.setString(3, user.getEmail());
                pst.setString(4, user.getPassword());
                return pst;
            }, keyHolder);
        user.setUserId((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("user_id"));
        return user;

    }

    @Override
    @Transactional
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE user_account SET first_name=?, last_name=?, email=?, password=? WHERE user_id=?",
                new Object[] { user.getFirstName(), user.getLast_name(), user.getEmail(), user.getPassword(), user.getUserId() }
        );
    }

    @Override
    public int deleteById(int userId) {
        return jdbcTemplate.update(
                "DELETE FROM user_account WHERE user_id=?",
                userId
        );
    }
}