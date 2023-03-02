package com.beckn.policyadmin.repository;

import com.beckn.policyadmin.model.Policy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolicyRepository extends MongoRepository<Policy, String> {
}
