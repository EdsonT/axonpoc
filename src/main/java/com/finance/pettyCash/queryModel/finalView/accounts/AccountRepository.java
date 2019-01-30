package com.finance.pettyCash.queryModel.finalView.accounts;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByCode(String code);

//    @Query("?#{[0]}")
//    List<Account> findByParam(BasicDBObject params);

    @Query("?#{[0]}")
    List<Account> findByParam(BasicDBObject params, Sort sort);
    @Query("?#{[0]}")
    List<Account> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    Account findByParam(BasicDBObject params);

    @Query("{'code': {'$in': ?0}}")
    List<Account> findByCodeIn(List<String> codes);
}
