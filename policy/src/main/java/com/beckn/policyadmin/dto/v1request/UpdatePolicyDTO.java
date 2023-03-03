package com.beckn.policyadmin.dto.v1request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UpdatePolicyDTO {
    private Context context;
    private UpdatePolicyRequest message;
}
