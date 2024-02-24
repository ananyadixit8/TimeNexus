package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    @Autowired
    ModelMapper modelMapper;

    public User build(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    public UserDto build(User user){
        return modelMapper.map(user, UserDto.class);
    }

    public User build(User user, UserDto userDto){
        modelMapper.map(userDto, user);
        return user;
    }


}
