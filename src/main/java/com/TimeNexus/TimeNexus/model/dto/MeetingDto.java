package com.TimeNexus.TimeNexus.model.dto;

import com.TimeNexus.TimeNexus.validator.EitherUserIdOrEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {

    @NotNull(message = "Duration of the meeting is a required field")
    @Min(value = 1, message = "Duration should be positive and greater than zero")
    private Integer duration;

    @NotBlank(message = "Subject cannot be null/empty")
    private String subject;

    @NotNull(message = "Meeting Time is a required field")
    @Future(message = "Meeting Time must be greater than current time")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    private Timestamp meetingTime;

    private String extraInfo;

    @Valid
    private List<MeetingParticipantDto> participants;

}
