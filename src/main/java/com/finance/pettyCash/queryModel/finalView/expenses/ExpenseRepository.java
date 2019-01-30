package com.finance.pettyCash.queryModel.finalView.expenses;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    Expense findByCode(String code);

    @Query("?#{[0]}")
    List<Expense> findByParam(BasicDBObject params);
    @Query("?#{[0]}")
    List<Expense> findByParam(BasicDBObject params, Sort sort);
    @Query("?#{[0]}")
    List<Expense> findByParam(BasicDBObject params, Pageable pageable);
}
