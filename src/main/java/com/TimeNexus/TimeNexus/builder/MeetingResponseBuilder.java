package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.MeetingResponse;
import com.TimeNexus.TimeNexus.model.dto.MeetingParticipantDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingResponseBuilder {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MeetingParticipantBuilder meetingParticipantBuilder;

    public MeetingResponse build(Meeting meeting){
        return modelMapper.map(meeting, MeetingResponse.class);
    }


}
