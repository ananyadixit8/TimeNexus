package com.TimeNexus.TimeNexus.model.dto;

import com.TimeNexus.TimeNexus.validator.EitherUserIdOrEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EitherUserIdOrEmail
public class MeetingParticipantDto {

    private Integer userId;

    @NotBlank(message = "First Name cannot be null/empty")
    private String firstName;

    @NotBlank(message = "Last Name cannot be null/empty")
    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is not valid")
    private String email;

}
