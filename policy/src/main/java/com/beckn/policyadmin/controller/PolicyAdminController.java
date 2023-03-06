package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.UpdatePolicyRequest;
import com.beckn.policyadmin.dto.v1response.PolicyMetaData;
import com.beckn.policyadmin.dto.v1response.PolicyResponce;
import com.beckn.policyadmin.exception.PolicyControllerException;
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
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/policy/{policyId}", "Policy id is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        try {
            Policy policy = policyAdminService.getPolicyById(policyId);
            return ResponseEntity.ok(policy);
        } catch (PolicyException e) {
            PolicyControllerException ex = new PolicyControllerException(
                    e.getType(), e.getCode().toString(), "/policy/{policyId}", e.getMessage()
            );
            return new ResponseEntity<>(ex, e.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/policy")
    public ResponseEntity<?> getPolicies() {
        try {
            List<PolicyMetaData> policies = policyAdminService.getPolicies();
            return ResponseEntity.ok(policies);
        } catch (PolicyException e) {
            PolicyControllerException ex = new PolicyControllerException(
                    e.getType(), e.getCode().toString(), "/policy", e.getMessage()
            );
            return new ResponseEntity<>(ex, e.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/policy")
    public ResponseEntity<?> updatePolicy(@RequestBody UpdatePolicyRequest policy) {
        if (policy.getPolicy().getId() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/policy", "Policy id is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (policy.getPolicy().getStatus() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/policy", "Policy status is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        try {
            Policy policyResponce = policyAdminService.updatePolicy(policy);
            PolicyResponce responce = new PolicyResponce(policyResponce.getId(), policyResponce.getStatus());
            return ResponseEntity.ok(responce);
        } catch (PolicyException e) {
            PolicyControllerException ex = new PolicyControllerException(
                    e.getType(), e.getCode().toString(), "/policy", e.getMessage()
            );
            return new ResponseEntity<>(ex, e.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
