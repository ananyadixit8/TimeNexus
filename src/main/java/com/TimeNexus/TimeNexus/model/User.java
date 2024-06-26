package com.TimeNexus.TimeNexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Class for User.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
