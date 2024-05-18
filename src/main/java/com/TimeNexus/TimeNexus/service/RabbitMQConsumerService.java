package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.model.ExecutionMessage;
import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import jakarta.validation.constraints.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;

@Service
public class RabbitMQConsumerService {

    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper for JSON deserialization

    @Autowired
    private RedisService redisProducerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "meetingsQueue") // Specify the queue to listen to
    public void receiveMessageFromMeetingQueue(Message message, Channel channel) {
        try {
            // Convert the message body (JSON string) into a Meeting object
            String jsonMeeting = new String(message.getBody());
            Meeting meeting = objectMapper.readValue(jsonMeeting, Meeting.class);

            // Process the Meeting object as needed
            System.out.println("Received Meeting from queue: " + meeting);

            redisProducerService.storeMeeting(meeting);

            // Acknowledge the message
             channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
             System.out.println("Message acknowledged");

        } catch (Exception e) {
            // Handle errors or exceptions
            System.err.println("Error processing message: " + e);
        }
    }

    @RabbitListener(queues = "executionQueue") // Specify the queue to listen to
    public void receiveMessageFromExecutionQueue(Message message, Channel channel) {
        try {
            // Convert the message body (JSON string) into a Meeting object
            String messageBody = new String(message.getBody());
            ExecutionMessage executionMessage = objectMapper.readValue(messageBody, ExecutionMessage.class);

            User participant = userService.getUserById(executionMessage.getUserId());

            // Process the Meeting object as needed
            System.out.println("Received execution message from queue: " + executionMessage);

            emailService.sendSimpleEmail(participant.getEmail(), executionMessage.getMeetingInfo());
            System.out.println("Email sent");

            // Acknowledge the message
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("Message acknowledged");

        } catch (Exception e) {
            // Handle errors or exceptions
            System.err.println("Error processing message: " + e);
            e.printStackTrace();
        }
    }

}
