package com.beckn.policyadmin.dto.v1response;

import com.beckn.policyadmin.dto.v1request.Context;
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
public class BroadcastResponce {
    private Context context;
    private MessageResponce message;
}
