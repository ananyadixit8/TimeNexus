package com.TimeNexus.TimeNexus.repository;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingInfo;

import java.util.List;

public interface MeetingInfoRepository {

    List<MeetingInfo> findAllForUser(int userId);

    List<MeetingInfo> findAll();

    MeetingInfo findById(int meetingId);

    Integer create(MeetingInfo meetingInfo);

    void update(MeetingInfo meetingInfo);

    int deleteById(int meetingId);

}
