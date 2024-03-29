package com.beckn.policyadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyControllerException {
    private String type;
    private String code;
    private String path;
    private String message;
}
