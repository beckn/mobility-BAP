package com.beckn.policyadmin.service;

import com.beckn.policyadmin.dto.v1request.V2PolicyDTO;
import com.beckn.policyadmin.mapper.V2PolicyBroadcastToPolicyMapper;
import com.beckn.policyadmin.model.V2Policy;
import com.beckn.policyadmin.repository.V2PolicyRepository;
import com.beckn.policyadmin.utility.CheckLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class V2PolicyAdminService {
    @Autowired
    private V2PolicyRepository policyRepository;

    @Autowired
    private V2PolicyBroadcastToPolicyMapper mapper;

    @Autowired
    private CheckLocation checkLocation;

    public V2Policy broadcastPolicy(V2PolicyDTO inputPolicy) {
        V2Policy policy = mapper.mapPolicyDtoToPolicy(inputPolicy);
        policy.setStatus("new");
        policy.setCreatedBy("System");
        policy.setLastModifiedBy("System");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        Date date = new Date();
        try {
            policy.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").parse(formatter.format(date)));
            policy.setLastModifiedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").parse(formatter.format(date)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return policyRepository.save(policy);
    }

}
