package com.TimeNexus.TimeNexus.repository;

import com.TimeNexus.TimeNexus.model.User;

import java.util.List;

/**
 * Interface to fetch user data from the database.
 */
public interface UserRepository {

    /**
     * Method to return all users from database.
     * @return List of User objects
     */
    List<User> findAll();

    /**
     * Method to return particular user mapped to given userId.
     * @param userId ID of the user to be fetched
     * @return User object corresponding to userId
     */
    User findById(int userId);

    /**
     * Method to create a new user in the database.
     * @param user User object to be created in database
     * @return User object
     */
    User create(User user);

    /**
     * Method to update a user in the database.
     * @param user User object, containing updated data.
     */
    void update(User user);

    /**
     * Method to delete a particular user.
     * @param userId UserId of user to be deleted
     * @return Boolean of whether user deleted or not
     */
    int deleteById(int userId);

}
