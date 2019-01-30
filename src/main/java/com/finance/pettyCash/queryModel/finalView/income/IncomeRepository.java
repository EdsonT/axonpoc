package com.finance.pettyCash.queryModel.finalView.income;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IncomeRepository extends MongoRepository<Income, String> {

    Income findByCode(String IncomeCode);

    @Query("?#{[0]}")
    List<Income> findByParam(BasicDBObject params);

    @Query("?#{[0]}")
    List<Income> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<Income> findByParam(BasicDBObject params, Pageable pageable);
}
