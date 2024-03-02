package com.TimeNexus.TimeNexus.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * Class for Meeting.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Meeting {

    private int meeting_id;
    private int duration;
    private String subject;
    private Timestamp meeting_time;
    private String extra_info;

}
