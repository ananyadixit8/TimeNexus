package com.TimeNexus.TimeNexus.repository.Impl;

import com.TimeNexus.TimeNexus.builder.MeetingParticipantBuilder;
import com.TimeNexus.TimeNexus.mapper.MeetingRowMapper;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.User;
import com.TimeNexus.TimeNexus.repository.UserMeetingMapperRepository;
import com.TimeNexus.TimeNexus.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserMeetingMapperRepositoryImpl implements UserMeetingMapperRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Autowired
    MeetingParticipantBuilder meetingParticipantBuilder;


    @Override
    public void saveParticipants(Meeting meeting) {

        // Save data in user_meeting table for participants, that is why is_host is set to false
        jdbcTemplate.batchUpdate("INSERT INTO user_meeting (meeting_id, user_id, is_host) VALUES (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                MeetingParticipant meetingParticipant = meeting.getParticipants().get(i);
                ps.setInt(1, meeting.getMeetingId());
                ps.setInt(2, meetingParticipant.getUserId());
                ps.setBoolean(3, meetingParticipant.getIsHost());
            }

            @Override
            public int getBatchSize() {
                return meeting.getParticipants().size();
            }
        });

    }

    public int getHost(int meetingId){
        Integer hostId = jdbcTemplate.queryForObject(
                "SELECT user_id from user_meeting where meeting_id = ? and is_host = TRUE",
                Integer.class,
                meetingId
        );
        return (hostId != null) ? hostId : -1;
    }

    public List<MeetingParticipant> getParticipants(int meetingId) {

        // Fetch participant IDs and their host status from the user_meeting table
        List<Map<String, Object>> participantData = jdbcTemplate.queryForList(
                "SELECT user_id, is_host FROM user_meeting WHERE meeting_id = ?",
                meetingId
        );

        // TO DO: Still not getting host

        return participantData.stream()
                .map(participant -> {
                    int userId = (int) participant.get("user_id");
                    boolean isHost = (boolean) participant.get("is_host");

                    log.info("This is host value: {}", isHost);

                    // Fetch user metadata using UserService
                    User user = userService.getUserById(userId);

                    // Build MeetingParticipant using MeetingParticipantBuilder
                    return meetingParticipantBuilder.build(user, isHost);
                })
                .collect(Collectors.toList());

    }

    @Override
    public Boolean isParticipant(int userId, int meetingId) {

        String sql = "SELECT COUNT(*) FROM user_meeting WHERE user_id = ? AND meeting_id = ?";

        // Execute the query and retrieve the count of rows
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, meetingId);

        // If count is greater than 0, the participant exists for the meeting; otherwise, they don't
        return count > 0;
    }


}
