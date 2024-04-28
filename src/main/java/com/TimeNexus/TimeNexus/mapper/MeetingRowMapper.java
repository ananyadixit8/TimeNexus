package com.TimeNexus.TimeNexus.mapper;

import com.TimeNexus.TimeNexus.model.Meeting;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingRowMapper implements RowMapper<Meeting> {

    @Override
    public Meeting mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Meeting(
                rs.getInt("meeting_id"),
                rs.getInt("duration"),
                rs.getString("subject"),
                rs.getTimestamp("meeting_time"),
                rs.getString("extra_info")
        );
    }

}
