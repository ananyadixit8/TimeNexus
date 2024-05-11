package com.TimeNexus.TimeNexus.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingInfo {

    private Integer duration;
    private String subject;
    private Timestamp meetingTime;
    private String extraInfo;

}
