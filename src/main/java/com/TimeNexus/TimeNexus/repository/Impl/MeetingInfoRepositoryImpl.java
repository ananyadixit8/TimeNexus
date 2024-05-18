package com.TimeNexus.TimeNexus.repository.Impl;

import com.TimeNexus.TimeNexus.mapper.MeetingInfoRowMapper;
import com.TimeNexus.TimeNexus.mapper.MeetingRowMapper;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.repository.MeetingInfoRepository;
import com.TimeNexus.TimeNexus.repository.UserMeetingMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class MeetingInfoRepositoryImpl implements MeetingInfoRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserMeetingMapperRepository userMeetingMapperRepository;

    @Override
    public List<MeetingInfo> findAllForUser(int userId) {
        return  jdbcTemplate.query(
                "SELECT * from meeting where meeting_id in (select meeting_id from user_meeting where user_id = ?)",
                new MeetingInfoRowMapper(),
                userId
        );

    }

    @Override
    public List<MeetingInfo> findAll() {
        return jdbcTemplate.query(
                "SELECT * from meeting",
                new MeetingInfoRowMapper()
        );
    }

    @Override
    public MeetingInfo findById(int meetingId) {

        return jdbcTemplate.queryForObject(
                "SELECT * from meeting where meeting_id = ?",
                new MeetingInfoRowMapper(),
                meetingId
        );
    }

    @Override
    public Integer create(MeetingInfo meetingInfo) {

        // Save meeting details in database
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO meeting (duration, subject, meeting_time, extra_info) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, meetingInfo.getDuration());
            pst.setString(2, meetingInfo.getSubject());
            pst.setTimestamp(3, meetingInfo.getMeetingTime());
            pst.setString(4, meetingInfo.getExtraInfo());
            return pst;
        }, keyHolder);

        return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("meeting_id");
    }

    @Override
    public void update(MeetingInfo meetingInfo) {

    }

    @Override
    public int deleteById(int meetingId) {
        return 0;
    }

}
