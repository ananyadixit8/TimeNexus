package com.TimeNexus.TimeNexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingParticipant {

    @Id
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isHost = Boolean.FALSE;

}
