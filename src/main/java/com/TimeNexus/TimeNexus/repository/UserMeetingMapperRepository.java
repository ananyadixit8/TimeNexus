package com.TimeNexus.TimeNexus.repository;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import com.TimeNexus.TimeNexus.model.User;

import java.util.List;

public interface UserMeetingMapperRepository {

    void saveParticipants(Meeting meeting);

    int getHost(int meetingId);

    List<MeetingParticipant> getParticipants(int meetingId);

    Boolean isParticipant(int userId, int meetingId);

}
