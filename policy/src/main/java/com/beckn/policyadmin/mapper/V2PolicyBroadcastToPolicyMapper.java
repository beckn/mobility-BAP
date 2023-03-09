package com.beckn.policyadmin.mapper;

import com.beckn.policyadmin.dto.v1request.*;
import com.beckn.policyadmin.model.V2Policy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class V2PolicyBroadcastToPolicyMapper {
    public V2Policy mapPolicyDtoToPolicy(V2PolicyDTO policyDTO) {
        V2Policy policy = new V2Policy();
        policy.setId(policyDTO.getMessage().getPolicy().getId());
        policy.setStatus(policyDTO.getMessage().getPolicy().getStatus().toLowerCase());
        policy.setDomain(policyDTO.getContext().getDomain());
        policy.setType(policyDTO.getMessage().getPolicy().getType());

        OwnerDescriptor ownerDescriptor = new OwnerDescriptor(
                policyDTO.getMessage().getPolicy().getOwner().getDescriptor().getName(),
                new Contact(policyDTO.getMessage().getPolicy().getOwner().getDescriptor().getContact().getEmail())
        );
        Owner owner = new Owner(ownerDescriptor);
        policy.setOwner(owner);

        Descriptor descriptor = new Descriptor(
                policyDTO.getMessage().getPolicy().getDescriptor().getName(),
                policyDTO.getMessage().getPolicy().getDescriptor().getShort_desc()
        );
        policy.setDescriptor(descriptor);

        Media media = new Media(
                policyDTO.getMessage().getPolicy().getMedia().get(0).getMimetype(),
                policyDTO.getMessage().getPolicy().getMedia().get(0).getUrl()
        );
        policy.setMedia(Collections.singletonList(media));

        Spatial spatial = new Spatial(policyDTO.getMessage().getPolicy().getCoverage().get(0).getSpatial().get(0).getCountry(),
                policyDTO.getMessage().getPolicy().getCoverage().get(0).getSpatial().get(0).getCity());
        Temporal temporal = new Temporal(new Range(policyDTO.getMessage().getPolicy().getCoverage().get(0).getTemporal().get(0).getRange().getStart(),
                policyDTO.getMessage().getPolicy().getCoverage().get(0).getTemporal().get(0).getRange().getEnd()));
        List<Subscriber> subscribers = new ArrayList<>(policyDTO.getMessage().getPolicy().getCoverage().get(0).getSubscribers());
        Coverage coverage = new Coverage();
        coverage.setSpatial(new ArrayList<>(Collections.singletonList(spatial)));
        coverage.setTemporal(new ArrayList<>(Collections.singletonList(temporal)));
        coverage.setSubscribers(subscribers);
        policy.setCoverage(new ArrayList<>(Collections.singletonList(coverage)));

        policy.setStart(policyDTO.getMessage().getPolicy().getCoverage().get(0).getTemporal().get(0).getRange().getStart());
        policy.setEnd(policyDTO.getMessage().getPolicy().getCoverage().get(0).getTemporal().get(0).getRange().getEnd());

        List<String> polygon = policyDTO.getMessage().getPolicy().getGeofences().get(0).getPolygon();
        Geofence geofence = new Geofence(polygon);
        policy.setGeofences(new ArrayList<>(Collections.singletonList(geofence)));

        return policy;
    }
}
