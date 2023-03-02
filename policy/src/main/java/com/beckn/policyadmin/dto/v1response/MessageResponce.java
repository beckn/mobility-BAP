package com.beckn.policyadmin.dto.v1response;


import com.beckn.policyadmin.dto.v1request.Acknowledge;
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
public class MessageResponce {
    private Acknowledge ack;
}
