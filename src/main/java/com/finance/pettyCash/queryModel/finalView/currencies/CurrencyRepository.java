package com.finance.pettyCash.queryModel.finalView.currencies;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
    Currency findByCode(String currencyCode);

    @Query("?#{[0]}")
    List<Currency> findByParam(BasicDBObject params, Sort sort);
    @Query("?#{[0]}")
    List<Currency> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    Currency findByParam(BasicDBObject params);
}
