package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.model.ExecutionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper for JSON serialization

    public void sendMessageToMeetingQueue(String queueName, Meeting meeting) {
        try {
            String jsonMeeting = objectMapper.writeValueAsString(meeting);
            rabbitTemplate.convertAndSend(queueName, jsonMeeting);
            System.out.println("Meeting sent to " + queueName + ": " + jsonMeeting);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Meeting object to JSON: " + e.getMessage());
        }
    }

    public void sendMessageToExecutionQueue(String queueName, Integer meetingId, Integer userId) {
        try {
            ExecutionMessage executionMessage = new ExecutionMessage(meetingId, userId);
            String jsonMeeting = objectMapper.writeValueAsString(executionMessage);
            rabbitTemplate.convertAndSend(queueName, jsonMeeting);
            System.out.println("Meeting sent to " + queueName + ": " + jsonMeeting);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Meeting object to JSON: " + e.getMessage());
        }
    }

}
