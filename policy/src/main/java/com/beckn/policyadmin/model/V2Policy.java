package com.beckn.policyadmin.model;

import com.beckn.policyadmin.dto.v1request.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "v2policy")
public class V2Policy {
    @Id
    private String id;
    private String status;
    private String domain;
    private Owner owner;
    private Descriptor descriptor;
    private List<Media> media;
    private String type;
    private List<Coverage> coverage;
    private List<Geofence> geofences;
    private Date start;
    private Date end;
    private String createdBy;
    private Date createdAt;
    private String lastModifiedBy;
    private Date lastModifiedAt;
}
