package com.finance.pettyCash.queryModel.finalView.activeYoungPersons;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FActiveYoungPersonsRepository extends MongoRepository<FActiveYoungPersons, String> {
    FActiveYoungPersons findByCode(String code);

    @Query("?#{[0]}")
    List<FActiveYoungPersons> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<FActiveYoungPersons> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    FActiveYoungPersons findByParam(BasicDBObject params);
}
