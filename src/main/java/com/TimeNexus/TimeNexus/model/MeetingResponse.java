package com.TimeNexus.TimeNexus.model;

import com.TimeNexus.TimeNexus.model.dto.MeetingParticipantDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingResponse {

    private int meetingId;
    private int duration;
    private String subject;
    private Timestamp meetingTime;
    private String extraInfo;
    private List<MeetingParticipant> participants;

}
