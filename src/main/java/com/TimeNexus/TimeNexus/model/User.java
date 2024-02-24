package com.TimeNexus.TimeNexus.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
