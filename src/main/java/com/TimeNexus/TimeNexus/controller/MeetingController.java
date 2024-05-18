package com.TimeNexus.TimeNexus.controller;

import com.TimeNexus.TimeNexus.model.MeetingResponse;
import com.TimeNexus.TimeNexus.model.dto.MeetingDto;
import com.TimeNexus.TimeNexus.service.MeetingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Meetings")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @GetMapping("/meetings")
    public ResponseEntity<List<MeetingResponse>> getAllMeetingsForUser(@RequestHeader("userId") int userId){
        List<MeetingResponse> meetings = meetingService.getAllMeetingsForUser(userId);
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MeetingResponse> createMeeting(@RequestHeader("userId") int userId, @Valid @RequestBody MeetingDto meetingDto){
        MeetingResponse meeting = meetingService.createMeeting(userId, meetingDto);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    @GetMapping("/meetings/{meetingId}")
    public ResponseEntity<MeetingResponse> getMeetingById(@RequestHeader("userId") int userId, @PathVariable int meetingId){
        MeetingResponse meeting = meetingService.getMeetingByIdForUser(userId, meetingId);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    @GetMapping("/allMeetings")
    public ResponseEntity<List<MeetingResponse>> getAllMeetings(){
        List<MeetingResponse> meetings = meetingService.getAllMeetings();
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

}
