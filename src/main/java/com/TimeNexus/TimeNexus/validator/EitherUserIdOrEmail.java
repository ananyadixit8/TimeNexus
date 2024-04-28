package com.TimeNexus.TimeNexus.validator;


import com.TimeNexus.TimeNexus.validator.Impl.EitherUserIdOrEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Constraint(validatedBy = {EitherUserIdOrEmailValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ReportAsSingleViolation
public @interface EitherUserIdOrEmail {
    String message() default "Either userId or email must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
