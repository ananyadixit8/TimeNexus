package com.TimeNexus.TimeNexus.model;

import lombok.*;

import java.util.List;

/**
 * Class for ErrorResponse.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    public List<String> messages;

}
