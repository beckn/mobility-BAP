package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.Acknowledge;
import com.beckn.policyadmin.dto.v1request.PolicyDTO;
import com.beckn.policyadmin.dto.v1request.UpdatePolicyDTO;
import com.beckn.policyadmin.dto.v1response.BroadcastResponce;
import com.beckn.policyadmin.dto.v1response.MessageResponce;
import com.beckn.policyadmin.dto.v1response.PolicyResponce;
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

        try {
            Policy policy = policyAdminService.broadcastPolicy(inputPolicy);
            BroadcastResponce broadcastResponce = new BroadcastResponce();
            broadcastResponce.setContext(inputPolicy.getContext());
            broadcastResponce.setMessage(new MessageResponce(new Acknowledge(new PolicyResponce(policy.getId(), policy.getStatus()))));
            return ResponseEntity.ok(broadcastResponce);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/broadcast/update")
    public ResponseEntity<?> updatePolicy(@RequestBody UpdatePolicyDTO inputPolicy) {
        if (inputPolicy.getMessage().getPolicy().getId() == null) {
            return new ResponseEntity<>("Policy id is missing.", HttpStatus.BAD_REQUEST);
        }

        try {
            Policy policy = policyAdminService.updatePolicy(inputPolicy.getMessage());
            BroadcastResponce broadcastResponce = new BroadcastResponce();
            broadcastResponce.setContext(inputPolicy.getContext());
            broadcastResponce.setMessage(new MessageResponce(new Acknowledge(new PolicyResponce(policy.getId(), policy.getStatus()))));
            return ResponseEntity.ok(broadcastResponce);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
