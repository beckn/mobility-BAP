package com.beckn.policyadmin.repository;

import com.beckn.policyadmin.model.Policy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface PolicyRepository extends MongoRepository<Policy, String> {
    @Query(value = "{ startDate :{$lte: ?0} , endDate :{$gte: ?0} , status : ?1}")
    List<Policy> findActivePoliciesOfCurrentDate(Date date, String status);
}
