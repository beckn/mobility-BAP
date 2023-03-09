package com.beckn.policyadmin.dto.v1request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class V2Policy {
    private String id;
    private String status;
    private String domain;
    private Owner owner;
    private Descriptor descriptor;
    private List<Media> media;
    private String type;
    private List<Coverage> coverage;
    private List<Geofence> geofences;
}
