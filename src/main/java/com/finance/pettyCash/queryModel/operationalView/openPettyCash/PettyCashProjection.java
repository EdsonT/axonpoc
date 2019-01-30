package com.finance.pettyCash.queryModel.operationalView.openPettyCash;

import com.finance.pettyCash.queryModel.BaseProjection;
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
public class PettyCashProjection extends BaseProjection {
    @Autowired
    PettyCashRepository pettyCashRepository;

    @Override
    @QueryHandler
    public PettyCash findOne(String code) {
        return null;
    }


    @QueryHandler
    public PettyCash findOneWithParams(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        return pettyCashRepository.findByParam(new BasicDBObject(params.toMap()));
    }


    @Override
    @QueryHandler
    public List<PettyCash> findAll(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        if (extractedParams.containsKey(LIMIT))
            return pettyCashRepository.findByParam(new BasicDBObject(params.toMap()),
                    PageRequest.of(
                            Integer.parseInt(extractedParams.getOrDefault("offset", 0).toString()),
                            Integer.parseInt(extractedParams.getOrDefault("limit", pettyCashRepository.findAll().size()).toString()),
                            Sort.Direction.fromString(
                                    extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
        else
            return pettyCashRepository.findByParam(new BasicDBObject(params.toMap()),
                    new Sort(Sort.Direction.fromString(
                            extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
    }
}
