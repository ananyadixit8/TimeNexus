package com.TimeNexus.TimeNexus.controller;

import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.UserDto;
import com.TimeNexus.TimeNexus.repository.UserRepository;
import com.TimeNexus.TimeNexus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Account")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        try{
            User user = userService.createuser(userDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable int user_id){
        try{
            User user = userService.getUserById(user_id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody UserDto userDto){
        try{
            User user = userService.updateUser(userId, userDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
