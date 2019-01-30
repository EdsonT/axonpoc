package com.finance.pettyCash.queryModel.operationalView.openPettyCash;

import com.finance.pettyCash.queryModel.finalView.accounts.Account;
import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PettyCashRepository extends MongoRepository<PettyCash, String> {

    //@Query("?#{[0]}")
    //List<PettyCash> findByParam(BasicDBObject params);

    @Query("?#{[0]}")
    List<PettyCash> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<PettyCash> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    PettyCash findByParam(BasicDBObject params);
}
