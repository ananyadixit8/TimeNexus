package com.TimeNexus.TimeNexus.repository;

import com.TimeNexus.TimeNexus.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(int userId);

    User create(User user);

    void update(User user);

    int deleteById(int userId);

}
