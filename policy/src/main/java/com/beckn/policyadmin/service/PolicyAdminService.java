package com.beckn.policyadmin.service;

import com.beckn.policyadmin.dto.v1request.PolicyDTO;
import com.beckn.policyadmin.dto.v1request.UpdatePolicyDTO;
import com.beckn.policyadmin.dto.v1request.UpdatePolicyRequest;
import com.beckn.policyadmin.dto.v1response.PolicyMetaData;
import com.beckn.policyadmin.exception.PolicyException;
import com.beckn.policyadmin.mapper.PolicyDtoToPolicyMapper;
import com.beckn.policyadmin.model.Policy;
import com.beckn.policyadmin.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyAdminService {
    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDtoToPolicyMapper mapper;

    public Policy getPolicyById(String policyId) {

        Optional<Policy> optionalPolicy = policyRepository.findById(policyId);
        if (optionalPolicy.isPresent()) {
            return optionalPolicy.get();
        }
        throw new PolicyException("", "", "E-101", "No data found in database for PolicyId : " + policyId);

    }

    public List<PolicyMetaData> getPolicies() {

        List<PolicyMetaData> policyMetaDataList = new ArrayList<>();
        List<Policy> policyList = policyRepository.findAll();

        for (Policy policy : policyList) {
            PolicyMetaData policyMetaData = new PolicyMetaData(
                    policy.getId(), policy.getStatus(), policy.getName(),
                    policy.getType(), policy.getStartDate(), policy.getEndDate());
            policyMetaDataList.add(policyMetaData);
        }
        return policyMetaDataList;
    }

    public Policy broadcastPolicy(PolicyDTO inputPolicy) {
        Policy policy = mapper.mapPolicyDtoToPolicy(inputPolicy);
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

    public Policy updatePolicy(UpdatePolicyDTO inputPolicy) {
        Optional<Policy> optionalPolicy = policyRepository.findById(inputPolicy.getMessage().getPolicy().getId());

        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setStatus(inputPolicy.getMessage().getPolicy().getStatus().toLowerCase());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            Date date = new Date();
            try {
                policy.setLastModifiedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").parse(formatter.format(date)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return policyRepository.save(policy);
        }
        throw new PolicyException("", "", "E-101", "Policy ID was not found in Database: " + inputPolicy.getMessage().getPolicy().getId());

    }

    public Policy updateMobilityPolicy(UpdatePolicyRequest inputPolicy) {
        Optional<Policy> optionalPolicy = policyRepository.findById(inputPolicy.getPolicy().getId());

        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();
            policy.setStatus(inputPolicy.getPolicy().getStatus().toLowerCase());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            Date date = new Date();
            try {
                policy.setLastModifiedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").parse(formatter.format(date)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return policyRepository.save(policy);
        }
        throw new PolicyException("", "", "E-101", "Policy ID was not found in Database: " + inputPolicy.getPolicy().getId());

    }
}
