package com.TimeNexus.TimeNexus.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for UserDto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "First Name cannot be null/empty")
    private String firstName;
    @NotBlank(message = "Last Name cannot be null/empty")
    private String lastName;
    @NotBlank(message = "Email cannot be null/empty")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is not valid")
    private String email;
    @NotBlank(message = "Password cannot be null/empty")
    private String password;

}
