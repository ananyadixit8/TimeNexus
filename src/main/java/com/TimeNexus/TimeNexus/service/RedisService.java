package com.TimeNexus.TimeNexus.service;


import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MeetingService meetingService;

    private void setExpirationTime(String key, Timestamp meetingTime) {

        // Calculate the expiration time based on the meetingTime
        LocalDateTime localMeetingTime = meetingTime.toLocalDateTime();
        Instant expirationInstant = localMeetingTime.atZone(ZoneId.systemDefault()).toInstant();

        // Set the expiration time for the key
        redisTemplate.expireAt(key, expirationInstant);

    }

    public Boolean storeMeetingInRedis(Integer meetingId){
        Meeting meeting = meetingService.getMeetingById(meetingId);
        storeMeeting(meeting);
        return true;
    }

    public void storeMeeting(Meeting meeting) {

        // Generate a unique key for storing meeting information in Redis
        String key = "meetingId:" + meeting.getMeetingId();

        redisTemplate.opsForValue().set(key, "");

        // Set the expiration time for the key
        setExpirationTime(key, meeting.getMeetingInfo().getMeetingTime());

        // Create a Redis set with the format meeting:{meetingId} and store userIds
        String setKey = "meetingIdToUserIds:" + meeting.getMeetingId();
        List<MeetingParticipant> participants = meeting.getParticipants();
        Set<Object> userIds = new HashSet<>();
        for (MeetingParticipant participant : participants) {
            userIds.add(participant.getUserId());
        }
        redisTemplate.opsForSet().add(setKey, userIds);

    }

}
