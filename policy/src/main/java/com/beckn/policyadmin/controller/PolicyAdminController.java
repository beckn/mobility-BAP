package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.UpdatePolicyRequest;
import com.beckn.policyadmin.dto.v1response.PolicyMetaData;
import com.beckn.policyadmin.dto.v1response.PolicyResponce;
import com.beckn.policyadmin.exception.PolicyException;
import com.beckn.policyadmin.model.Policy;
import com.beckn.policyadmin.service.PolicyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PolicyAdminController {

    @Autowired
    private PolicyAdminService policyAdminService;

    @GetMapping(value = "/policy/{policyId}")
    public ResponseEntity<?> getPolicyById(@PathVariable String policyId) {
        if (policyId == null) {
            return new ResponseEntity<>("Policy id is missing.", HttpStatus.BAD_REQUEST);
        }
        try {
            Policy policy = policyAdminService.getPolicyById(policyId);
            return ResponseEntity.ok(policy);
        } catch (PolicyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/policy")
    public ResponseEntity<?> getPolicies() {
        try {
            List<PolicyMetaData> policies = policyAdminService.getPolicies();
            return ResponseEntity.ok(policies);
        } catch (PolicyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value = "/policy")
    public ResponseEntity<?> updatePolicy(@RequestBody UpdatePolicyRequest policy) {
        try {
            Policy policyResponce = policyAdminService.updateMobilityPolicy(policy);
            PolicyResponce responce = new PolicyResponce(policyResponce.getId(), policyResponce.getStatus());
            return ResponseEntity.ok(responce);
        } catch (PolicyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
