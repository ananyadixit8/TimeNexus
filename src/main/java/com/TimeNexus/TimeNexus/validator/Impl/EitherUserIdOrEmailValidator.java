package com.TimeNexus.TimeNexus.validator.Impl;


import com.TimeNexus.TimeNexus.model.dto.MeetingParticipantDto;
import com.TimeNexus.TimeNexus.validator.EitherUserIdOrEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EitherUserIdOrEmailValidator implements ConstraintValidator<EitherUserIdOrEmail, Object> {

    @Override
    public void initialize(EitherUserIdOrEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are handled by @NotNull
        }

        if (value instanceof MeetingParticipantDto) {
            MeetingParticipantDto meetingParticipantDto = (MeetingParticipantDto) value;
            Integer userId = meetingParticipantDto.getUserId();
            String email = meetingParticipantDto.getEmail();

            log.info("Got user ID as {} ", userId);

            // Both userId and email are not provided
            if (userId == null && email == null) {
                return false; // Return false because both fields are required
            }

            // Either userId or email is provided
            return true;
        }

        return false;
    }

}

