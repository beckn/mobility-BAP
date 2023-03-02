package com.beckn.policyadmin.mapper;

import com.beckn.policyadmin.dto.v1request.PolicyDTO;
import com.beckn.policyadmin.model.Policy;
import org.springframework.stereotype.Component;

@Component
public class PolicyDtoToPolicyMapper {
    public Policy mapPolicyDtoToPolicy(PolicyDTO policyDTO){
        Policy policy = new Policy();
        policy.setId(policyDTO.getPolicy().getId());
        policy.setDomain(policyDTO.getPolicy().getDomain());
        policy.setType(policyDTO.getPolicy().getType());
        policy.setCountry(policyDTO.getPolicy().getCountry());
        policy.setCity(policyDTO.getPolicy().getCity());
        policy.setName(policyDTO.getPolicy().getName());
        policy.setDescription(policyDTO.getPolicy().getDescription());
        policy.setOwner(policyDTO.getPolicy().getOwner());
        policy.setContactEmail(policyDTO.getPolicy().getContactEmail());
        policy.setPolicyDocuments(policyDTO.getPolicy().getPolicyDocuments());
        policy.setStartDate(policyDTO.getPolicy().getStartDate());
        policy.setEndDate(policyDTO.getPolicy().getEndDate());
        policy.setApplicableTo(policyDTO.getPolicy().getApplicableTo());
        policy.setPolygon(policyDTO.getPolicy().getPolygon());
        policy.setRules(policyDTO.getPolicy().getRules());
        policy.setStatus(policyDTO.getPolicy().getStatus().toLowerCase());

        return policy;
    }
}
