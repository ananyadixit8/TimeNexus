package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.builder.UserBuilder;
import com.TimeNexus.TimeNexus.exception.UserNotFoundException;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.UserDto;
import com.TimeNexus.TimeNexus.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

/**
 * Service class for User.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBuilder userBuilder;

    /**
     * Method to create a new user.
     * @param userDto User data as received from request body
     * @return Newly created User object
     */
    @Transactional
    public User createuser(UserDto userDto){

        // First converting the userDto to user object,
        // then creating the new user
        User user = Stream.of(userDto)
                .map(userBuilder::build)
                .map(userRepository::create)
                .findAny().orElseThrow();
        log.info("Created new user with id {} successfully.", user.getUserId());
        return user;

    }

    /**
     * Method to fetch all users from database.
     * @return List of User objects
     */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Method to fetch user from given userId.
     * @param userId userId as passed in request
     * @return User object
     * @throws UserNotFoundException
     */
    public User getUserById(int userId) throws UserNotFoundException {

        try{
            return userRepository.findById(userId);
        } catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

    }

    /**
     * Method to update a user.
     * @param userId userId as passed in request
     * @param userDto Updated User data as received from request body
     * @return Updated User object
     */
    @Transactional
    public User updateUser(int userId, UserDto userDto){

        User currentUser;
        // Find the current user from the database
        try{
            currentUser = userRepository.findById(userId);
        } catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        // Create the updated user, according to request
        User updatedUser = userBuilder.build(currentUser, userDto);
        userRepository.update(updatedUser);

        log.info("Updated user with id {} successfully.", currentUser.getUserId());
        return updatedUser;

    }

}
