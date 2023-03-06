package com.beckn.policyadmin.dto.v1request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coverage {
    private List<Spatial> spatial;
    private List<Temporal> temporal;
    private List<Subscriber> subscribers;

}
