package com.finance.pettyCash.queryModel.finalView.expenses;

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
public class ExpenseProjection extends BaseProjection {
    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    @QueryHandler
    public Expense findOne(String code) {
        return expenseRepository.findByCode(code);
    }

    @Override
    @QueryHandler
    public List<Expense> findAll(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();

        for (String key : RESERVED_KEYWORDS) {
            if (params.has(key)) {
                extractedParams.put(key, params.get(key));
                params.remove(key);
            }
        }
        if (extractedParams.containsKey(LIMIT))
            return expenseRepository.findByParam(new BasicDBObject(params.toMap()),
                    PageRequest.of(
                            Integer.parseInt(extractedParams.getOrDefault("offset", 0).toString()),
                            Integer.parseInt(extractedParams.getOrDefault("limit", expenseRepository.findAll().size()).toString()),
                            Sort.Direction.fromString(
                                    extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
        else
            return expenseRepository.findByParam(new BasicDBObject(params.toMap()),
                    new Sort(Sort.Direction.fromString(
                            extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));

    }
}
