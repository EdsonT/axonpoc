package com.finance.pettyCash.queryModel.finalView.activeHouses;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FActiveHousesRepository extends MongoRepository<FActiveHouses, String> {
    FActiveHouses findByCode(String code);

    @Query("?#{[0]}")
    List<FActiveHouses> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<FActiveHouses> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    FActiveHouses findByParam(BasicDBObject params);
}
