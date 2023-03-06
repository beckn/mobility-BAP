package com.beckn.policyadmin.model;

import com.beckn.policyadmin.dto.v1request.Media;
import com.beckn.policyadmin.dto.v1request.Subscriber;
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
    private String domain;
    private String type;
    private String country;
    private String city;
    private String name;
    private String description;
    private String owner;
    private String contactEmail;
    private List<Media> policyDocuments;
    private Date startDate;
    private Date endDate;
    private List<Subscriber> applicableTo;
    private List<String> polygon;
    private JSONObject rules;
    private String status;
    private String createdBy;
    private Date createdAt;
    private String lastModifiedBy;
    private Date lastModifiedAt;
}
