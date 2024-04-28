package com.TimeNexus.TimeNexus.repository.Impl;

import com.TimeNexus.TimeNexus.mapper.MeetingRowMapper;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.repository.MeetingRepository;
import com.TimeNexus.TimeNexus.repository.UserMeetingMapperRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserMeetingMapperRepository userMeetingMapperRepository;

    @Override
    public List<Meeting> findAll(int userId) {
        List<Meeting> meetings =  jdbcTemplate.query(
                "SELECT * from meeting where meeting_id in (select meeting_id from user_meeting where user_id = ?)",
                new MeetingRowMapper(),
                userId
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
        return jdbcTemplate.queryForObject(
                "SELECT * from meeting where meeting_id = ?",
                new MeetingRowMapper(),
                meetingId
        );
    }

    @Override
    public Meeting create(Meeting meeting) {

        // Save meeting details in database
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO meeting (duration, subject, meeting_time, extra_info) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, meeting.getDuration());
            pst.setString(2, meeting.getSubject());
            pst.setTimestamp(3, meeting.getMeetingTime());
            pst.setString(4, meeting.getExtraInfo());
            return pst;
        }, keyHolder);
        meeting.setMeetingId((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("meeting_id"));

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
