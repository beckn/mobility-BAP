package com.beckn.policyadmin.mapper;

import com.beckn.policyadmin.dto.v1request.*;
import com.beckn.policyadmin.dto.v1response.V2PolicyResponce;
import com.beckn.policyadmin.model.V2Policy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class V2PolicyToPolicyResponceMapper {
    public V2PolicyResponce mapPolicyToPolicyResponce(V2Policy policy) {
        V2PolicyResponce policyResponce = new V2PolicyResponce();
        policyResponce.setId(policy.getId());
        policyResponce.setStatus(policy.getStatus());
        policyResponce.setDomain(policy.getDomain());
        policyResponce.setType(policy.getType());

        OwnerDescriptor ownerDescriptor = new OwnerDescriptor(
                policy.getOwner().getDescriptor().getName(),
                new Contact(policy.getOwner().getDescriptor().getContact().getEmail())
        );
        Owner owner = new Owner(ownerDescriptor);
        policyResponce.setOwner(owner);

        Descriptor descriptor = new Descriptor(
                policy.getDescriptor().getName(),
                policy.getDescriptor().getShort_desc()
        );
        policyResponce.setDescriptor(descriptor);

        Media media = new Media(
                policy.getMedia().get(0).getMimetype(),
                policy.getMedia().get(0).getUrl()
        );
        policyResponce.setMedia(Collections.singletonList(media));

        Spatial spatial = new Spatial(policy.getCoverage().get(0).getSpatial().get(0).getCountry(),
                policy.getCoverage().get(0).getSpatial().get(0).getCity());
        Temporal temporal = new Temporal(new Range(policy.getCoverage().get(0).getTemporal().get(0).getRange().getStart(),
                policy.getCoverage().get(0).getTemporal().get(0).getRange().getEnd()));
        List<Subscriber> subscribers = new ArrayList<>(policy.getCoverage().get(0).getSubscribers());
        Coverage coverage = new Coverage();
        coverage.setSpatial(new ArrayList<>(Collections.singletonList(spatial)));
        coverage.setTemporal(new ArrayList<>(Collections.singletonList(temporal)));
        coverage.setSubscribers(subscribers);
        policyResponce.setCoverage(new ArrayList<>(Collections.singletonList(coverage)));

        List<String> polygon = policy.getGeofences().get(0).getPolygon();
        Geofence geofence = new Geofence(polygon);
        policyResponce.setGeofences(new ArrayList<>(Collections.singletonList(geofence)));

        return policyResponce;
    }
}
