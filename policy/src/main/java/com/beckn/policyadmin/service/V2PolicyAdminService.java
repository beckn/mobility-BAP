package com.beckn.policyadmin.service;

import com.beckn.policyadmin.dto.v1request.LocationRequest;
import com.beckn.policyadmin.dto.v1request.UpdatePolicyRequest;
import com.beckn.policyadmin.dto.v1request.V2PolicyDTO;
import com.beckn.policyadmin.dto.v1response.LocationPolicyViolation;
import com.beckn.policyadmin.dto.v1response.PolicyMetaData;
import com.beckn.policyadmin.dto.v1response.ViolationResponce;
import com.beckn.policyadmin.exception.PolicyException;
import com.beckn.policyadmin.mapper.V2PolicyBroadcastToPolicyMapper;
import com.beckn.policyadmin.model.V2Policy;
import com.beckn.policyadmin.repository.V2PolicyRepository;
import com.beckn.policyadmin.utility.CheckLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public V2Policy getPolicyById(String policyId) {

        Optional<V2Policy> optionalPolicy = policyRepository.findById(policyId);
        if (optionalPolicy.isPresent()) {
            return optionalPolicy.get();
        }
        throw new PolicyException(
                "Application error", HttpStatus.NOT_FOUND, "", "No data found for the Policy id: " + policyId
        );
    }

    public List<PolicyMetaData> getPolicies() {

        List<PolicyMetaData> policyMetaDataList = new ArrayList<>();
        List<V2Policy> policyList = policyRepository.findAll();
        if (policyList.isEmpty())
            throw new PolicyException(
                    "Application error", HttpStatus.NOT_FOUND, "", "No data found in the DB."
            );

        for (V2Policy policy : policyList) {
            PolicyMetaData policyMetaData = new PolicyMetaData(
                    policy.getId(), policy.getStatus(), policy.getOwner().getDescriptor().getName(),
                    policy.getType(), policy.getStart(), policy.getEnd());
            policyMetaDataList.add(policyMetaData);
        }
        return policyMetaDataList;
    }

    public V2Policy updatePolicy(UpdatePolicyRequest inputPolicy) {
        Optional<V2Policy> optionalPolicy = policyRepository.findById(inputPolicy.getPolicy().getId());

        if (optionalPolicy.isPresent()) {
            V2Policy policy = optionalPolicy.get();
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


        List<V2Policy> policyList = policyRepository.findActivePoliciesOfCurrentDate(new Date(), "applied");
        if (policyList.isEmpty())
            throw new PolicyException(
                    "Application error", HttpStatus.NOT_FOUND, "", "No data found in DB."
            );
        Map<String, List<String>> policyLocationMap = new HashMap<>();
        for (V2Policy policy : policyList) {
            policyLocationMap.put(policy.getId(), policy.getGeofences().get(0).getPolygon());
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
