package com.TimeNexus.TimeNexus.repository;

import com.TimeNexus.TimeNexus.dto.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(Long user_id);

    User create(User user);

    User update(User user);

    int deleteById(Long user_id);

}
