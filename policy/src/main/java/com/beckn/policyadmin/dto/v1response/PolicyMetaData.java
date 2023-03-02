package com.beckn.policyadmin.dto.v1response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "policy")
public class PolicyMetaData {
    private String id;
    private String status;
    private String name;
    private String type;
    private Date startDate;
    private Date endDate;

}
