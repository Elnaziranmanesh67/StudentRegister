package com.example.studentregister.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RunTimeExceptionResponse {

    private String message;

    private HttpStatus status;

    private Date time;
}
