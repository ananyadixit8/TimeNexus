package com.TimeNexus.TimeNexus.builder;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.dto.MeetingDto;
import com.TimeNexus.TimeNexus.model.dto.MeetingParticipantDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingBuilder {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MeetingParticipantBuilder meetingParticipantBuilder;

    public Meeting build(MeetingDto meetingDto) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Configure the mappings between MeetingDto and Meeting classes
        modelMapper.typeMap(MeetingDto.class, Meeting.class)
                .addMappings(mapper -> mapper.skip(Meeting::setMeetingId));

        modelMapper.typeMap(MeetingDto.class, MeetingInfo.class)
                .addMappings(mapper -> {
                    mapper.map(MeetingDto::getDuration, MeetingInfo::setDuration);
                    mapper.map(MeetingDto::getSubject, MeetingInfo::setSubject);
                    mapper.map(MeetingDto::getMeetingTime, MeetingInfo::setMeetingTime);
                    mapper.map(MeetingDto::getExtraInfo, MeetingInfo::setExtraInfo);
                });

        // Configure the mappings between MeetingParticipantDto and MeetingParticipant classes
        modelMapper.typeMap(MeetingParticipantDto.class, MeetingParticipant.class)
                .addMappings(mapper -> mapper.map(MeetingParticipantDto::getUserId, MeetingParticipant::setUserId));

        // Now, convert MeetingDto to Meeting
        Meeting meeting = modelMapper.map(meetingDto, Meeting.class);

        // TODO: fix this code
        meeting.setMeetingInfo(new MeetingInfo(meetingDto.getDuration(),
                meetingDto.getSubject(),
                meetingDto.getMeetingTime(),
                meetingDto.getExtraInfo())
        );

        // Convert MeetingParticipantDto objects to MeetingParticipant objects
        List<MeetingParticipant> participants = meetingDto.getParticipants().stream()
                .map(meetingParticipantBuilder::build)
                .collect(Collectors.toList());

        meeting.setParticipants(participants);

        return meeting;
    }

    public MeetingDto build(Meeting meeting) {

        MeetingDto meetingDto = modelMapper.map(meeting.getMeetingInfo(), MeetingDto.class);

        // Set participants from Meeting object to MeetingDto object
        meetingDto.setParticipants(meeting.getParticipants().stream()
                .map(meetingParticipantBuilder::build)
                .collect(Collectors.toList()));

        return meetingDto;

    }

    public Meeting build(Meeting meeting, MeetingDto meetingDto) {

        MeetingInfo meetingInfo = modelMapper.map(meetingDto, MeetingInfo.class);
        meeting.setMeetingInfo(meetingInfo);
        return meeting;

    }

}
