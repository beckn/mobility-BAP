package com.beckn.policyadmin.dto.v1response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViolationResponce {
    private List<LocationPolicyViolation> policyCheckResult;
}
