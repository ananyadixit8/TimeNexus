package com.TimeNexus.TimeNexus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.Collections;
import java.util.Set;

@Component
public class MeetingExpirationListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        // Get the expired key from the message
        String expiredKey = new String(message.getBody());
        System.out.println("Meeting expired: " + expiredKey);

        // Extract the meeting ID from the key using pattern matching
        java.util.regex.Pattern regexPattern = java.util.regex.Pattern.compile("meetingId:(\\d+)");
        java.util.regex.Matcher matcher = regexPattern.matcher(expiredKey);
        if (matcher.find()) {
            // Extract the meeting ID as an integer
            int meetingId = Integer.parseInt(matcher.group(1));
            System.out.println("Meeting ID: " + meetingId);

            String setKey = "meetingIdToUserIds:" + meetingId;

            // Retrieve the values associated with the expired key
            Set<String> values = redisTemplate.opsForSet().members(setKey);
            if (values != null) {

                // Process the values as needed
                for (String value : values) {

                    String[] valueArray = value.split(",\\s*"); // Split on comma with optional whitespace

                    for (String str : valueArray) {
                        Integer intValue = (Integer.parseInt(str.trim())); // Parse each substring to an integer
                        System.out.println("Value: " + intValue);
                        rabbitMQProducerService.sendMessageToExecutionQueue("executionQueue", meetingId, intValue);
                    }

                }
            } else {
                System.err.println("No values found for set key: " + setKey);
            }

            // Delete the set from Redis
            redisTemplate.delete(setKey);

            // Handle the expired meeting here, such as sending notifications or updating data
        } else {
            System.err.println("Error extracting meeting ID from key: " + expiredKey);
        }

    }
}
