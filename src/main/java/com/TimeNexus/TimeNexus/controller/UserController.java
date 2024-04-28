package com.TimeNexus.TimeNexus.controller;

import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.UserDto;
import com.TimeNexus.TimeNexus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for all User APIs.
 */
@RestController
@RequestMapping("/Account")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Method signifying the get all users API.
     * @return List of all users fetched from the database
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Method signifying the create new user API.
     * @param userDto UserDto object created from the request body received,
     *                using which new user will be created
     * @return Newly created user
     */
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto){
        User user = userService.createuser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Method signifying the get a particular User by ID API.
     * @param user_id ID of the user to be fetched from the database
     * @return User object corresponding to userId, if exists
     */
    @GetMapping("/users/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable int user_id){
            User user = userService.getUserById(user_id);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Method signifying the update user API.
     * @param userId ID of the user to be updated in the database
     * @param userDto UserDto object created from the request body received,
     *                using which user will be updated
     * @return Updated user object.
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @Valid @RequestBody UserDto userDto){
        User user = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
