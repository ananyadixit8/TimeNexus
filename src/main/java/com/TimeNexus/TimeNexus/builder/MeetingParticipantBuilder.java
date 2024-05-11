package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.MeetingParticipantDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class MeetingParticipantBuilder {

    @Autowired
    ModelMapper modelMapper;

    public MeetingParticipantDto build(MeetingParticipant meetingParticipant){
        return modelMapper.map(meetingParticipant, MeetingParticipantDto.class);
    }

    public MeetingParticipant build(User user){
        return modelMapper.map(user, MeetingParticipant.class);
    }

    public MeetingParticipant build(User user, Boolean isHost){
        MeetingParticipant participant = modelMapper.map(user, MeetingParticipant.class);
        participant.setIsHost(isHost);

        log.info("Participant is {}", participant.toString());

        return participant;
    }

    public MeetingParticipant build(MeetingParticipantDto meetingParticipantDto){
        return modelMapper.map(meetingParticipantDto, MeetingParticipant.class);
    }

}
