package com.beckn.policyadmin.repository;

import com.beckn.policyadmin.model.V2Policy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface V2PolicyRepository extends MongoRepository<V2Policy, String> {}
