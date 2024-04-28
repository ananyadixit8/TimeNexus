package com.TimeNexus.TimeNexus.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExecutionMessage {

    // TODO: Might change to MeetingIfo later?
    Meeting meeting;
    Integer userId;

}
