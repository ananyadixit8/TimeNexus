package com.TimeNexus.TimeNexus.service;


import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.MeetingParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    public void storeMeeting(Meeting meeting) {
        // Generate a unique key for storing meeting information in Redis
        String key = "meeting:" + meeting.getMeetingId();

        // Iterate over participants and store userIds in Redis
        List<MeetingParticipant> participants = meeting.getParticipants();
        for (MeetingParticipant participant : participants) {
            Integer userId = participant.getUserId();
            // Store the userId in a Redis set under the generated key
            redisTemplate.opsForSet().add(key, userId);
        }

        // Calculate the expiration time based on the meetingTime
        LocalDateTime meetingTime = meeting.getMeetingTime().toLocalDateTime();
        Instant expirationInstant = meetingTime.atZone(ZoneId.systemDefault()).toInstant();

        // Set the expiration time for the key
        redisTemplate.expireAt(key, expirationInstant);
    }


    @Scheduled(fixedRate = 300000) // Run every 5 minutes (300,000 milliseconds)
    public void checkAndProcessExpiringKeys() {
        // Get all keys from Redis
        Set<String> keys = redisTemplate.keys("meeting:*");

        Instant now = Instant.now();
        Instant fiveMinutesLater = now.plus(Duration.ofMinutes(5));

        // Iterate over keys and check if they are about to expire
        for (String key : keys) {
            Duration remainingTime = Duration.ofSeconds(redisTemplate.getExpire(key));
            if (remainingTime != null && remainingTime.compareTo(Duration.ofMinutes(5)) <= 0) {
                // Key is about to expire in 5 minutes or less
                // Retrieve the values associated with the key
                Set<Object> values = redisTemplate.opsForSet().members(key);

                // Process the values as needed
                for (Object value : values) {
                    // Here, 'key' represents the meeting and 'value' represents the userId
                    System.out.println("Key: " + key + ", Value: " + value);
                    // Send the key-value pair to RabbitMQ queue
                    rabbitMQProducerService.sendMessageToExecutionQueue("executionQueue", Integer.valueOf(key), (Integer) value);
                }

                // Delete the key from Redis
                redisTemplate.delete(key);
            }
        }
    }

}
