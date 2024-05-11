package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.MeetingResponse;
import com.TimeNexus.TimeNexus.model.dto.MeetingDto;
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

    public MeetingResponse build(Meeting meeting){

        // Configure the mappings between Meeting and MeetingResponse classes
        modelMapper.typeMap(Meeting.class, MeetingResponse.class)
                .addMapping(src -> src.getMeetingInfo().getDuration(), MeetingResponse::setDuration)
                .addMapping(src -> src.getMeetingInfo().getSubject(), MeetingResponse::setSubject)
                .addMapping(src -> src.getMeetingInfo().getMeetingTime(), MeetingResponse::setMeetingTime)
                .addMapping(src -> src.getMeetingInfo().getExtraInfo(), MeetingResponse::setExtraInfo);

        // Map Meeting object to MeetingResponse object
        return modelMapper.map(meeting, MeetingResponse.class);

    }


}
