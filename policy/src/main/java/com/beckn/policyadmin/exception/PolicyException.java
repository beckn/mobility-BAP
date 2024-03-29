package com.beckn.policyadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyException extends RuntimeException{
    private String type;
    private HttpStatus code;
    private String path;
    private String message;
}
