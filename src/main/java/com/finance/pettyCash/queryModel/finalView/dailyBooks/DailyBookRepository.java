package com.finance.pettyCash.queryModel.finalView.dailyBooks;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface DailyBookRepository extends MongoRepository<DailyBook, String> {
    DailyBook findByFinancialCode(String financialCode);

    // @Query("?#{[0]}")
    // List<DailyBook> findByParam(BasicDBObject params);

    @Query("?#{[0]}")
    List<DailyBook> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<DailyBook> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    DailyBook findByParam(BasicDBObject params);

    @Query(value = "{'houseCode': ?0, 'registerDate': {'$gt': ?1}}", sort = "{'registerDate': 1}")
    List<DailyBook> findByHouseCodeAndRegisterDateGreaterThan(String houseCode, Date registerDate);

    @Query(value = "{'aggregateCode': ?0, 'houseCode': ?1, 'registerDate': {'$gt': ?2}}", sort = "{'registerDate': 1}")
    List<DailyBook> findByAggregateCodeAndHouseCodeAndRegisterDateGreaterThan(String aggregateCode, String houseCode, Date registerDate);

    @Query(value = "{'aggregateCode': ?0, 'houseCode': ?1, 'accountCode': {'$nin': ?2}}", sort = "{'registerDate': -1}")
    List<DailyBook> findByAggregateCodeAndHouseCode(String aggregateCode, String houseCode, List<String> codes);
}