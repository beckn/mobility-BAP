package com.beckn.policyadmin.repository;

import com.beckn.policyadmin.model.V2Policy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface V2PolicyRepository extends MongoRepository<V2Policy, String> {
    @Query(value = "{ start :{$lte: ?0} , end :{$gte: ?0} , status : ?1}")
    List<V2Policy> findActivePoliciesOfCurrentDate(Date date, String status);
}
