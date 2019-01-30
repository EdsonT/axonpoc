package com.finance.pettyCash.queryModel.finalView.currencies;

import com.finance.pettyCash.queryModel.BaseProjection;
import com.finance.pettyCash.queryModel.finalView.accounts.Account;
import com.mongodb.BasicDBObject;
import org.axonframework.queryhandling.QueryHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CurrencyProjection extends BaseProjection {
    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    @QueryHandler
    public Currency findOne(String code) {
        return currencyRepository.findByCode(code);
    }

    @QueryHandler
    public Currency findOneWithParams(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        return currencyRepository.findByParam(new BasicDBObject(params.toMap()));
    }

    @Override
    @QueryHandler
    public List<Currency> findAll(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        if (extractedParams.containsKey(LIMIT))
            return currencyRepository.findByParam(new BasicDBObject(params.toMap()),
                    PageRequest.of(
                            Integer.parseInt(extractedParams.getOrDefault("offset", 0).toString()),
                            Integer.parseInt(extractedParams.getOrDefault("limit", currencyRepository.findAll().size()).toString()),
                            Sort.Direction.fromString(
                                    extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
        else
            return currencyRepository.findByParam(new BasicDBObject(params.toMap()),
                    new Sort(Sort.Direction.fromString(
                            extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));

    }
}
