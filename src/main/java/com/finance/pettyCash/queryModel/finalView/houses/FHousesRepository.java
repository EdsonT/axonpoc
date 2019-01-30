package com.finance.pettyCash.queryModel.finalView.houses;

import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FHousesRepository extends MongoRepository<FHouses, String> {
    FHouses findByCode(String code);

    @Query("?#{[0]}")
    List<FHouses> findByParam(BasicDBObject params, Sort sort);

    @Query("?#{[0]}")
    List<FHouses> findByParam(BasicDBObject params, Pageable pageable);

    @Query("?#{[0]}")
    FHouses findByParam(BasicDBObject params);
}
