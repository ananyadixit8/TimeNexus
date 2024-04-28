package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.builder.MeetingBuilder;
import com.TimeNexus.TimeNexus.builder.MeetingParticipantBuilder;
import com.TimeNexus.TimeNexus.builder.MeetingResponseBuilder;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.MeetingResponse;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.model.dto.MeetingDto;
import com.TimeNexus.TimeNexus.repository.MeetingRepository;
import com.TimeNexus.TimeNexus.repository.UserMeetingMapperRepository;
import com.TimeNexus.TimeNexus.validator.MeetingValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserService userService;
    private final MeetingBuilder meetingBuilder;
    private final MeetingValidator meetingValidator;
    private final UserMeetingMapperRepository userMeetingMapperRepository;
    private final MeetingResponseBuilder meetingResponseBuilder;
    private final MeetingParticipantBuilder meetingParticipantBuilder;
    private final RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository,
                          UserService userService,
                          MeetingBuilder meetingBuilder,
                          MeetingValidator meetingValidator,
                          UserMeetingMapperRepository userMeetingMapperRepository,
                          MeetingResponseBuilder meetingResponseBuilder,
                          MeetingParticipantBuilder meetingParticipantBuilder,
                          RabbitMQProducerService rabbitMQProducerService){
        this.meetingRepository = meetingRepository;
        this.userService = userService;
        this.meetingBuilder = meetingBuilder;
        this.meetingValidator = meetingValidator;
        this.userMeetingMapperRepository = userMeetingMapperRepository;
        this.meetingResponseBuilder = meetingResponseBuilder;
        this.meetingParticipantBuilder = meetingParticipantBuilder;
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    private MeetingParticipant buildMeetingHost(int userId){

        User hostUser = userService.getUserById(userId);
        MeetingParticipant host =  meetingParticipantBuilder.build(hostUser);
        host.setIsHost(true);
        return host;
    }

    public MeetingResponse createMeeting(int userId, MeetingDto meetingDto){

        Meeting meeting = meetingBuilder.build(meetingDto);

        // Get the host of the meeting and add it to list of meeting participants
        meeting.getParticipants().add(buildMeetingHost(userId));

        meetingValidator.validateParticipants(meeting.getParticipants());

        meeting = meetingRepository.create(meeting);

        userMeetingMapperRepository.saveParticipants(meeting);

        // Send meeting to rabbitMQ queue
        rabbitMQProducerService.sendMessageToMeetingQueue("meetingsQueue", meeting);

        return meetingResponseBuilder.build(meeting);
    }

    public List<MeetingResponse> getAllMeetings(int userId){

        // Returning all the meetings for this user
        return meetingRepository.findAll(userId).stream()
                .map(meetingResponseBuilder::build)
                .collect(Collectors.toList());

    }

    public MeetingResponse getMeetingById(int userId, int meetingId) {

        // Currently allowing only host to access complete meeting info
        // TO DO: Decide on behavior to get meeting a user is participant of
        meetingValidator.validateHost(userId, meetingId);
        Meeting meeting = meetingRepository.findById(meetingId);

        List<MeetingParticipant> participants = userMeetingMapperRepository.getParticipants(meetingId);

        meeting.setParticipants(participants);

        return meetingResponseBuilder.build(meeting);

    }
}
