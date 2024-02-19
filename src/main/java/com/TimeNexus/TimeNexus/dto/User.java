package com.TimeNexus.TimeNexus.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

}
