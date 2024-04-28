package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
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

    @RabbitListener(queues = "meetingsQueue") // Specify the queue to listen to
    public void receiveMessage(Message message, Channel channel) {
        try {
            // Convert the message body (JSON string) into a Meeting object
            String jsonMeeting = new String(message.getBody());
            Meeting meeting = objectMapper.readValue(jsonMeeting, Meeting.class);

            // Process the Meeting object as needed
            System.out.println("Received Meeting from queue: " + meeting);

            // TODO: Add processing for message
            redisProducerService.storeMeeting(meeting);

            // Acknowledge the message
            // TODO: Uncomment after implementing processing logic
             channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
             System.out.println("Message acknowledged");

        } catch (Exception e) {
            // Handle errors or exceptions
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

}
