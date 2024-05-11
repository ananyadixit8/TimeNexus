package com.TimeNexus.TimeNexus.repository;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;

import java.util.List;

public interface MeetingRepository {

    List<Meeting> findAll(int userId);

    MeetingInfo findById(int meetingId);

    Meeting create(Meeting meeting);

    void update(Meeting meeting);

    int deleteById(int meetingId);

}
