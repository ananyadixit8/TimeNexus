package com.TimeNexus.TimeNexus.mapper;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingInfoRowMapper  implements RowMapper<MeetingInfo> {

    @Override
    public MeetingInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MeetingInfo(
                rs.getInt("duration"),
                rs.getString("subject"),
                rs.getTimestamp("meeting_time"),
                rs.getString("extra_info")
        );
    }

}
