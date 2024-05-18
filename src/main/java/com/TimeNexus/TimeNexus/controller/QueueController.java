package com.TimeNexus.TimeNexus.controller;

import com.TimeNexus.TimeNexus.model.Meeting;
import com.TimeNexus.TimeNexus.service.MeetingService;
import com.TimeNexus.TimeNexus.service.RabbitMQProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Queues")
public class QueueController {

    @Autowired
    RabbitMQProducerService producerService;

    @Autowired
    MeetingService meetingService;

    @GetMapping("/insertInMeetingQueue")
    public ResponseEntity<String> insertIntoMeetingQueue(@RequestHeader("meetingId") int meetingId){

        Meeting meeting = meetingService.getMeetingById(meetingId);

        producerService.sendMessageToMeetingQueue("meetingsQueue", meeting);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
