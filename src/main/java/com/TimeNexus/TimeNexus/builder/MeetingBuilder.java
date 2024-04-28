package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.dto.MeetingDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeetingBuilder {

    @Autowired
    ModelMapper modelMapper;

    public Meeting build(MeetingDto meetingDto){
        return modelMapper.map(meetingDto, Meeting.class);
    }

    public MeetingDto build(Meeting meeting){
        return modelMapper.map(meeting, MeetingDto.class);
    }

    public Meeting build(Meeting meeting, MeetingDto meetingDto){
        modelMapper.map(meetingDto, Meeting.class);
        return meeting;
    }

}
