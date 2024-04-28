package com.TimeNexus.TimeNexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.List;

/**
 * Class for Meeting.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Meeting {

    @Id
    private Integer meetingId;
    private Integer duration;
    private String subject;
    private Timestamp meetingTime;
    private String extraInfo;
    private List<MeetingParticipant> participants;

    public Meeting(int meetingId, int duration, String subject, Timestamp meetingTime, String extraInfo) {
        this.meetingId = meetingId;
        this.duration = duration;
        this.subject = subject;
        this.meetingTime = meetingTime;
        this.extraInfo = extraInfo;
    }

}
