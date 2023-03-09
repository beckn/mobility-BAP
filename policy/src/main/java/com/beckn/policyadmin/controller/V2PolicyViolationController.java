package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.LocationRequest;
import com.beckn.policyadmin.dto.v1response.ViolationResponce;
import com.beckn.policyadmin.exception.PolicyControllerException;
import com.beckn.policyadmin.exception.PolicyException;
import com.beckn.policyadmin.service.V2PolicyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/policy")
public class V2PolicyViolationController {

    @Autowired
    private V2PolicyAdminService policyAdminService;

    @PostMapping(value = "/checkViolation/location")
    public ResponseEntity<?> checkViolation(@RequestBody LocationRequest locationRequest) {
        if (locationRequest.getLocations().isEmpty()) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/checkViolation/location", "Locations are missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        try {
            ViolationResponce responce = policyAdminService.checkViolation(locationRequest);
            return ResponseEntity.ok(responce);
        } catch (PolicyException e) {
            PolicyControllerException ex = new PolicyControllerException(
                    e.getType(), e.getCode().toString(), "/checkViolation/location", e.getMessage()
            );
            return new ResponseEntity<>(ex, e.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
