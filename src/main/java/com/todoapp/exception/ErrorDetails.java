package com.todoapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String path;
    private String message;
}
