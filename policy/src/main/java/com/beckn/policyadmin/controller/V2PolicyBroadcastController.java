package com.beckn.policyadmin.controller;

import com.beckn.policyadmin.dto.v1request.Acknowledge;
import com.beckn.policyadmin.dto.v1request.V2PolicyDTO;
import com.beckn.policyadmin.dto.v1response.MessageResponce;
import com.beckn.policyadmin.dto.v1response.PolicyResponce;
import com.beckn.policyadmin.dto.v1response.V2BroadcastResponce;
import com.beckn.policyadmin.model.V2Policy;
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
public class V2PolicyBroadcastController {

    @Autowired
    private V2PolicyAdminService policyAdminService;

    @PostMapping(value = "/broadcast")
    public ResponseEntity<?> broadcastPolicy(@RequestBody V2PolicyDTO inputPolicy) {

        try {
            V2Policy policy = policyAdminService.broadcastPolicy(inputPolicy);
            V2BroadcastResponce broadcastResponce = new V2BroadcastResponce();
            broadcastResponce.setContext(inputPolicy.getContext());
            broadcastResponce.setMessage(new MessageResponce(new Acknowledge(new PolicyResponce(policy.getId(), policy.getStatus()))));
            return ResponseEntity.ok(broadcastResponce);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
