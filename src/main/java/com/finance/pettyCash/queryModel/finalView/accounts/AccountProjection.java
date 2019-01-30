package com.finance.pettyCash.queryModel.finalView.accounts;

import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.queryModel.BaseProjection;
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


@Component
public class AccountProjection extends BaseProjection {
    @Autowired
    AccountRepository accountsRepository;

    @Override
    @QueryHandler
    public Account findOne(String code) {
        return accountsRepository.findByCode(code);
    }
    @QueryHandler
    public Account findOneWithParams(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        return accountsRepository.findByParam(new BasicDBObject(params.toMap()));
    }

    @Override
    @QueryHandler
    public List<Account> findAll(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        if (extractedParams.containsKey(LIMIT))
            return accountsRepository.findByParam(new BasicDBObject(params.toMap()),
                    PageRequest.of(
                            Integer.parseInt(extractedParams.getOrDefault("offset", 0).toString()),
                            Integer.parseInt(extractedParams.getOrDefault("limit", accountsRepository.findAll().size()).toString()),
                            Sort.Direction.fromString(
                                    extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
        else
            return accountsRepository.findByParam(new BasicDBObject(params.toMap()),
                    new Sort(Sort.Direction.fromString(
                            extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));

    }

    @QueryHandler
    public List<Account> findByCodeIn(Account accountQuery) {
        HashMap<String, Object> extractedParams = new HashMap<>();
        List<String> codes = Arrays.asList(Statics.openCode, Statics.closeCode);
        return accountsRepository.findByCodeIn(codes);
    }

}
