package com.TimeNexus.TimeNexus.validator;

import com.TimeNexus.TimeNexus.builder.UserBuilder;
import com.TimeNexus.TimeNexus.exception.UserNotFoundException;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.repository.UserMeetingMapperRepository;
import com.TimeNexus.TimeNexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingValidator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMeetingMapperRepository userMeetingMapperRepository;

    @Autowired
    UserBuilder userBuilder;

    public void validateParticipants(List<MeetingParticipant> participants){
        for(MeetingParticipant participant: participants){

            User participantUser = userBuilder.build(participant);
            validateUserExists(participantUser);
        }
    }

    public void validateUserExists(User user){
        if (user == null){
            throw new UserNotFoundException("User cannot be null.");
        }
        try{
            userRepository.findById(user.getUserId());
        } catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException("User with ID " + user.getUserId() + " not found.");
        }
    }

    public void validateHost(int userId, int meetingId){

        // Find host of meeting
        int hostId = userMeetingMapperRepository.getHost(meetingId);
        // if(userId != hostId){
        // TODO: throw correct exception
        // }

    }

}
