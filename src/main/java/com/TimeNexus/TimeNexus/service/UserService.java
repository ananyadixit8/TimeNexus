package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.builder.UserBuilder;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.UserDto;
import com.TimeNexus.TimeNexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBuilder userBuilder;

    public User createuser(UserDto userDto){

        // First converting the userDto to user object,
        // then creating the new user
        return Stream.of(userDto)
                .map(userBuilder::build)
                .map(userRepository::create)
                .findAny().orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int userId){
        return userRepository.findById(userId);
    }

    public User updateUser(int userId, UserDto userDto){

        // Find the current user from the database
        // TO DO: Raise error if user not found
        User current_user = userRepository.findById(userId);
        // Create the updated user, according to request
        User updated_user = userBuilder.build(current_user, userDto);
        userRepository.update(updated_user);
        return updated_user;
    }

}
