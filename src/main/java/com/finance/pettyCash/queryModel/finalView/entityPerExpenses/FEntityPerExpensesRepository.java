package com.finance.pettyCash.queryModel.finalView.entityPerExpenses;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FEntityPerExpensesRepository extends MongoRepository<FEntityPerExpenses, String> {
    @Query("?#{[0]}")
    List<FEntityPerExpenses> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<FEntityPerExpenses> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    FEntityPerExpenses findByParam(BasicDBObject params);
}
