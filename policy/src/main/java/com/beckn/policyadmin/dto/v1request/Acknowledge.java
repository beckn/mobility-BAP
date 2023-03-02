package com.beckn.policyadmin.dto.v1request;


import com.beckn.policyadmin.dto.v1response.PolicyResponce;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Acknowledge {
    private PolicyResponce policy;
}
