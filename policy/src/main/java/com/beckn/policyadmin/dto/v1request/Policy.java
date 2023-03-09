package com.beckn.policyadmin.dto.v1request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Policy {
    private String id;
    private String domain;
    private String type;
    private String country;
    private String city;
    private String name;
    private String description;
    private String owner;
    private String contactEmail;
    private String policyDocuments;
    private Date startDate;
    private Date endDate;
    private List<String> applicableTo;
    private List<String> polygon;
    private String rules;
    private String status;
}
