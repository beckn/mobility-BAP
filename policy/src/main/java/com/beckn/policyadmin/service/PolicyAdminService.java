package com.beckn.policyadmin.service;

import com.beckn.policyadmin.dto.v1request.LocationRequest;
import com.beckn.policyadmin.dto.v1request.PolicyDTO;
import com.beckn.policyadmin.dto.v1request.UpdatePolicyRequest;
import com.beckn.policyadmin.dto.v1response.LocationPolicyViolation;
import com.beckn.policyadmin.dto.v1response.PolicyMetaData;
import com.beckn.policyadmin.dto.v1response.ViolationResponce;
import com.beckn.policyadmin.exception.PolicyException;
import com.beckn.policyadmin.mapper.PolicyDtoToPolicyMapper;
import com.beckn.policyadmin.model.Policy;
import com.beckn.policyadmin.repository.PolicyRepository;
import com.beckn.policyadmin.utility.CheckLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PolicyAdminService {
    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyDtoToPolicyMapper mapper;

    @Autowired
    private CheckLocation checkLocation;

    public Policy getPolicyById(String policyId) {

        Optional<Policy> optionalPolicy = policyRepository.findById(policyId);
        if (optionalPolicy.isPresent()) {
            return optionalPolicy.get();
        }
        throw new PolicyException(
                "Application error", HttpStatus.NOT_FOUND, "", "No data found for the Policy id: " + policyId
        );
    }

    public List<PolicyMetaData> getPolicies() {

        List<PolicyMetaData> policyMetaDataList = new ArrayList<>();
        List<Policy> policyList = policyRepository.findAll();
        if (policyList.isEmpty())
            throw new PolicyException(
                    "Application error", HttpStatus.NOT_FOUND, "", "No data found in the DB."
            );

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

    public Policy updatePolicy(UpdatePolicyRequest inputPolicy) {
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
        throw new PolicyException(
                "Application error", HttpStatus.NOT_FOUND, "", "No data found for the Policy id: " + inputPolicy.getPolicy().getId()
        );
    }

    public ViolationResponce checkViolation(LocationRequest locationRequest) {


        List<Policy> policyList = policyRepository.findActivePoliciesOfCurrentDate(new Date(), "applied");
        Map<String, List<String>> policyLocationMap = new HashMap<>();
        for (Policy policy : policyList) {
            policyLocationMap.put(policy.getId(), policy.getPolygon());
        }

        List<LocationPolicyViolation> locationPolicyViolations = new ArrayList<>();

        for (String location : locationRequest.getLocations()) {
            List<String> violatedPolicies = new ArrayList<>();
            LocationPolicyViolation locationPolicyViolation = new LocationPolicyViolation(location, false, violatedPolicies);
            for (Map.Entry<String, List<String>> entry : policyLocationMap.entrySet()) {
                if (CheckLocation.checkInside(entry.getValue(), location) == 1) {
                    if (!locationPolicyViolation.getViolation())
                        locationPolicyViolation.setViolation(true);
                    violatedPolicies.add(entry.getKey());
                }
            }
            locationPolicyViolation.setViolatedPolicies(violatedPolicies);
            locationPolicyViolations.add(locationPolicyViolation);
        }
        ViolationResponce responce = new ViolationResponce();
        responce.setPolicyCheckResult(locationPolicyViolations);
        return responce;
    }
}
