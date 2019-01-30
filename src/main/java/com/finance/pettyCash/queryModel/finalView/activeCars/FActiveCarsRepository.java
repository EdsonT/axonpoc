package com.finance.pettyCash.queryModel.finalView.activeCars;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FActiveCarsRepository extends MongoRepository<FActiveCars, String> {
    FActiveCars findByCode(String code);

    @Query("?#{[0]}")
    List<FActiveCars> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<FActiveCars> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    FActiveCars findByParam(BasicDBObject params);
}
