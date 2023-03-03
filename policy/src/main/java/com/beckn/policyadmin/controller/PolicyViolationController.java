package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.LocationRequest;
import com.beckn.policyadmin.dto.v1response.ViolationResponce;
import com.beckn.policyadmin.service.PolicyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/policy")
public class PolicyViolationController {

    @Autowired
    private PolicyAdminService policyAdminService;

    @PostMapping(value = "/checkViolation/location")
    public ResponseEntity<?> checkViolation(@RequestBody LocationRequest locationRequest) {

        try {
            ViolationResponce responce = policyAdminService.checkViolation(locationRequest);
            return ResponseEntity.ok(responce);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
