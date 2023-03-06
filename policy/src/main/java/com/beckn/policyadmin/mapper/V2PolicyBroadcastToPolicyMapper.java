package com.beckn.policyadmin.mapper;

import com.beckn.policyadmin.dto.v1request.V2PolicyDTO;
import com.beckn.policyadmin.model.V2Policy;
import org.springframework.stereotype.Component;

@Component
public class V2PolicyBroadcastToPolicyMapper {
    public V2Policy mapPolicyDtoToPolicy(V2PolicyDTO policyDTO) {
        V2Policy policy = new V2Policy();
        policy.setId(policyDTO.getMessage().getPolicy().getId());
        policy.setDomain(policyDTO.getContext().getDomain());
        policy.setType(policyDTO.getMessage().getPolicy().getType());
        policy.setCountry(policyDTO.getMessage().getPolicy().getCoverage().get(0).getSpatial().get(0).getCountry());
        policy.setCity(policyDTO.getMessage().getPolicy().getCoverage().get(0).getSpatial().get(0).getCity());
        policy.setName(policyDTO.getMessage().getPolicy().getDescriptor().getName());
        policy.setDescription(policyDTO.getMessage().getPolicy().getDescriptor().getShort_desc());
        policy.setOwner(policyDTO.getMessage().getPolicy().getOwner().getDescriptor().getName());
        policy.setContactEmail(policyDTO.getMessage().getPolicy().getOwner().getDescriptor().getContact().getEmail());
        policy.setPolicyDocuments(policyDTO.getMessage().getPolicy().getMedia());
        policy.setStartDate(policyDTO.getMessage().getPolicy().getCoverage().get(0).getTemporal().get(0).getRange().getStart());
        policy.setEndDate(policyDTO.getMessage().getPolicy().getCoverage().get(0).getTemporal().get(0).getRange().getEnd());
        policy.setApplicableTo(policyDTO.getMessage().getPolicy().getCoverage().get(0).getSubscribers());
        policy.setPolygon(policyDTO.getMessage().getPolicy().getGeofences().get(0).getPolygon());
        policy.setRules(policyDTO.getMessage().getPolicy().getRules());

        return policy;
    }
}
