package com.beckn.policyadmin.dto.v1response;

import com.beckn.policyadmin.dto.v1request.V2Context;
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
public class V2BroadcastResponce {
    private V2Context context;
    private MessageResponce message;
}
