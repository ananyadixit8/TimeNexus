package com.TimeNexus.TimeNexus.repository.Impl;

import com.TimeNexus.TimeNexus.mapper.MeetingRowMapper;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.repository.MeetingInfoRepository;
import com.TimeNexus.TimeNexus.repository.MeetingRepository;
import com.TimeNexus.TimeNexus.repository.UserMeetingMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserMeetingMapperRepository userMeetingMapperRepository;

    @Autowired
    MeetingInfoRepository meetingInfoRepository;

    @Override
    public List<Meeting> findAllMeetingsForUser(int userId) {

        // Fetch meeting IDs associated with the user
        List<Integer> meetingIds = userMeetingMapperRepository.getMeetingIdsForUser(userId);

        List<Meeting> meetings = new ArrayList<>();

        // Fetch MeetingInfo and MeetingParticipants for each meeting ID
        for (Integer meetingId : meetingIds) {

            MeetingInfo meetingInfo = meetingInfoRepository.findById(meetingId);
            List<MeetingParticipant> participants = userMeetingMapperRepository.getParticipants(meetingId);

            // Create Meeting object and add to the list
            Meeting meeting = new Meeting(meetingId, meetingInfo, participants);
            meetings.add(meeting);
        }

        return meetings;
    }

    @Override
    public List<Meeting> findAllMeetings(){

        List<Meeting> meetings =  jdbcTemplate.query(
                "SELECT * from meeting",
                new MeetingRowMapper()
        );

        for (Meeting meeting : meetings
        ) {
            List<MeetingParticipant> participants = userMeetingMapperRepository.getParticipants(meeting.getMeetingId());
            meeting.setParticipants(participants);
        }

        return meetings;
    }

    @Override
    public Meeting findById(int meetingId) {

        MeetingInfo meetingInfo = meetingInfoRepository.findById(meetingId);
        List<MeetingParticipant> participants = userMeetingMapperRepository.getParticipants(meetingId);
        return new Meeting(meetingId, meetingInfo, participants);

    }

    @Override
    public Meeting create(Meeting meeting) {

        // Save meeting details in database
        Integer meetingId = meetingInfoRepository.create(meeting.getMeetingInfo());

        meeting.setMeetingId(meetingId);

        // Save participants in the database
        userMeetingMapperRepository.saveParticipants(meeting);

        return meeting;

    }

    @Override
    public void update(Meeting meeting) {

    }

    @Override
    public int deleteById(int meetingId) {
        return 0;
    }
}
