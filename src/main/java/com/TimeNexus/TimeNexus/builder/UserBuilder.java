package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class to map UserDto to User class and vice versa.
 * This will be used when converting request body from user
 * to User object.
 */
@Component
public class UserBuilder {

    @Autowired
    ModelMapper modelMapper;

    /**
     * Method to convert a UserDto object into a User object.
     *
     * @param userDto UserDto object as received from request
     * @return User object
     */
    public User build(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    /**
     * Method to convert User object into a UserDto object.
     *
     * @param user User object to be converted to Dto
     * @return UserDto object
     */
    public UserDto build(User user){
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Method to create an updated User object from the given User and UserDto
     * object.
     *
     * @param user User object to be updated from Dto object
     * @param userDto UserDto object as received from request
     * @return Updated User object
     */
    public User build(User user, UserDto userDto){
        modelMapper.map(userDto, user);
        return user;
    }

}
