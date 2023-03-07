package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.Acknowledge;
import com.beckn.policyadmin.dto.v1request.PolicyDTO;
import com.beckn.policyadmin.dto.v1request.UpdatePolicyDTO;
import com.beckn.policyadmin.dto.v1response.BroadcastResponce;
import com.beckn.policyadmin.dto.v1response.MessageResponce;
import com.beckn.policyadmin.dto.v1response.PolicyResponce;
import com.beckn.policyadmin.exception.PolicyControllerException;
import com.beckn.policyadmin.exception.PolicyException;
import com.beckn.policyadmin.model.Policy;
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
public class PolicyBroadcastController {

    @Autowired
    private PolicyAdminService policyAdminService;

    @PostMapping(value = "/broadcast")
    public ResponseEntity<?> broadcastPolicy(@RequestBody PolicyDTO inputPolicy) {
        ResponseEntity<PolicyControllerException> exception = validateRequest(inputPolicy);
        if (exception != null) return exception;

        try {
            Policy policy = policyAdminService.broadcastPolicy(inputPolicy);
            BroadcastResponce broadcastResponce = new BroadcastResponce();
            broadcastResponce.setContext(inputPolicy.getContext());
            broadcastResponce.setMessage(new MessageResponce(new Acknowledge(new PolicyResponce(policy.getId(), policy.getStatus()))));
            return ResponseEntity.ok(broadcastResponce);
        } catch (PolicyException e) {
            PolicyControllerException ex = new PolicyControllerException(
                    e.getType(), e.getCode().toString(), "/broadcast", e.getMessage()
            );
            return new ResponseEntity<>(ex, e.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static ResponseEntity<PolicyControllerException> validateRequest(PolicyDTO inputPolicy) {
        if (inputPolicy.getContext().getDomain() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast", "Domain value is missing in context."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (inputPolicy.getContext().getAction() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast", "Action value is missing in context."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }

        if (inputPolicy.getPolicy().getId() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast", "Policy id is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (inputPolicy.getPolicy().getName() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast", "Policy name is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (inputPolicy.getPolicy().getType() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast", "Policy type is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PostMapping(value = "/broadcast/update")
    public ResponseEntity<?> updatePolicy(@RequestBody UpdatePolicyDTO inputPolicy) {
        if (inputPolicy.getMessage().getPolicy().getId() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast/update", "Policy id is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (inputPolicy.getMessage().getPolicy().getStatus() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast/update", "Policy status is missing."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (inputPolicy.getContext().getAction() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast/update", "Action is missing in context."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        if (inputPolicy.getContext().getDomain() == null) {
            PolicyControllerException exception = new PolicyControllerException(
                    "Application error", HttpStatus.BAD_REQUEST.toString(), "/broadcast/update", "Domain id is missing in context."
            );
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }

        try {
            Policy policy = policyAdminService.updatePolicy(inputPolicy.getMessage());
            BroadcastResponce broadcastResponce = new BroadcastResponce();
            broadcastResponce.setContext(inputPolicy.getContext());
            broadcastResponce.setMessage(new MessageResponce(new Acknowledge(new PolicyResponce(policy.getId(), policy.getStatus()))));
            return ResponseEntity.ok(broadcastResponce);
        } catch (PolicyException e) {
            PolicyControllerException ex = new PolicyControllerException(
                    e.getType(), e.getCode().toString(), "/broadcast/update", e.getMessage()
            );
            return new ResponseEntity<>(ex, e.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
