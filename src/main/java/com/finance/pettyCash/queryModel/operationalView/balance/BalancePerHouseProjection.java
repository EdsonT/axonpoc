package com.finance.pettyCash.queryModel.operationalView.balance;

import com.finance.pettyCash.commons.enums.Errors;

import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import com.finance.pettyCash.queryModel.BaseProjection;
import com.finance.pettyCash.queryModel.SearchQuery;
import com.finance.pettyCash.services.QueryValidatorService;
import com.finance.pettyCash.commons.exceptions.BaseException;
import com.mongodb.BasicDBObject;
import org.axonframework.queryhandling.QueryHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BalancePerHouseProjection extends BaseProjection {
    @Autowired
    BalancePerHouseRepository balancePerHouseRepository;

    @Autowired
    QueryValidatorService queryValidatorService;

    @Override
    @QueryHandler
    public BalancePerHouse findOne(String code) {
        return null;
    }

    @QueryHandler
    public BalancePerHouse findOneWithParams(JSONObject params) throws BaseException {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        if (extractedParams != null)
            try {
                return balancePerHouseRepository.findByParam(new BasicDBObject(params.toMap()));
            } catch (Exception e) {
                throw new BaseException(Arrays.asList(Errors.INVALID_REQUEST));
            }
        else
            throw new BaseException(Arrays.asList(Errors.INVALID_REQUEST));

    }

    @QueryHandler(queryName = "searchQuery")
    public List<BalancePerHouse> search(SearchQuery searchQuery) {
        BasicDBObject query = new BasicDBObject();
        StringBuilder sb = new StringBuilder(".*").append(searchQuery.getKeyword()).append(".*");
        if (searchQuery.getFields() == null)
            query.append("houseCode", new BasicDBObject("$regex", sb.toString()));
        else for (String field : searchQuery.getFields()) {
            query.append(field, new BasicDBObject("$regex", sb.toString()));
        }

        System.out.println(query);
        return balancePerHouseRepository.searchByParam(query);

    }

    @Override
    @QueryHandler
    public List<BalancePerHouse> findAll(JSONObject params) throws BaseException {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        ValidatorHelper validation = queryValidatorService.paramsValidator(extractedParams, BalancePerHouse.class);
        if (validation.isValid())
            if (extractedParams.containsKey(LIMIT))
                return balancePerHouseRepository.findByParam(new BasicDBObject(params.toMap()),
                        PageRequest.of(
                                Integer.parseInt(extractedParams.getOrDefault(OFFSET, 0).toString()),
                                Integer.parseInt(extractedParams.getOrDefault(LIMIT, balancePerHouseRepository.findAll().size()).toString()),
                                Sort.Direction.fromString(
                                        extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                                extractedParams.getOrDefault(ORDER_BY, "id").toString()));
            else
                return balancePerHouseRepository.findByParam(new BasicDBObject(params.toMap()),
                        new Sort(Sort.Direction.fromString(
                                extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                                extractedParams.getOrDefault(ORDER_BY, "id").toString()));
        else
            throw new BaseException(validation.getErrors());
    }
}
