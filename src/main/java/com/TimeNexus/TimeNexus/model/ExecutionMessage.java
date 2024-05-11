package com.TimeNexus.TimeNexus.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExecutionMessage {

    MeetingInfo meetingInfo;
    Integer userId;

}
