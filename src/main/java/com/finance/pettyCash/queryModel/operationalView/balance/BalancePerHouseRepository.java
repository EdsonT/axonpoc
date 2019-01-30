package com.finance.pettyCash.queryModel.operationalView.balance;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BalancePerHouseRepository extends MongoRepository<BalancePerHouse, String> {

//    @Query("?#{[0]}")
//    List<BalancePerHouse> findByParam(BasicDBObject params);

    @Query("?#{[0]}")
    List<BalancePerHouse> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<BalancePerHouse> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    BalancePerHouse findByParam(BasicDBObject params);

    @Query("?#{[0]}")
    List<BalancePerHouse> searchByParam(BasicDBObject params);

}
