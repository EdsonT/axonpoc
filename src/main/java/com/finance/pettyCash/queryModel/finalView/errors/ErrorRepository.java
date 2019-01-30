package com.finance.pettyCash.queryModel.finalView.errors;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ErrorRepository extends MongoRepository<ErrorView, String> {
//    @Query("{'alias': ?0}")
    ErrorView findByAlias(String code);
}
