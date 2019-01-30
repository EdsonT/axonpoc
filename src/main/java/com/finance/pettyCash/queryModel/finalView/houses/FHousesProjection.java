package com.finance.pettyCash.queryModel.finalView.houses;

import com.finance.pettyCash.commons.enums.Errors;
import com.finance.pettyCash.commons.exceptions.BaseException;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import com.finance.pettyCash.queryModel.BaseProjection;
import com.finance.pettyCash.services.QueryValidatorService;
import com.mongodb.BasicDBObject;
import org.axonframework.queryhandling.QueryHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
class FHousesProjection extends BaseProjection {
    @Autowired
    FHousesRepository fHousesRepository;

    @Autowired
    QueryValidatorService queryValidatorService;

    @Override
    @QueryHandler
    public FHouses findOne(String code) {
        FHouses fHouses = fHousesRepository.findByCode(code);
        return fHouses;
    }

    @QueryHandler
    public FHouses findOneWithParams(JSONObject params) {
        HashMap<String, Object> extractedParams = new HashMap<>();
                  FHouses data;
                        for (String key : RESERVED_KEYWORDS) {
                            if (params.has(key)) {
                                extractedParams.put(key, params.get(key));
                                params.remove(key);
                            }
                       }
                       if(!extractedParams.isEmpty())
                           throw new BaseException(Arrays.asList(Errors.INVALID_REQUEST));
                       try {
                           data = fHousesRepository.findByParam(new BasicDBObject(params.toMap()));
                       } catch (IncorrectResultSizeDataAccessException e) {
                           throw new BaseException(Arrays.asList(Errors.INVALID_RESULT_DATA));
                       }
                       return data;
    }

    @Override
    @QueryHandler
    public List<FHouses> findAll(JSONObject params) {
          HashMap<String, Object> extractedParams = new HashMap<>();

                        for (String key : RESERVED_KEYWORDS) {
                            if (params.has(key)) {
                                extractedParams.put(key, params.get(key));
                                params.remove(key);
                            }
                        }
                        ValidatorHelper validation = queryValidatorService.paramsValidator(extractedParams, FHouses.class);
                         if (!validation.isValid())
                             throw new BaseException(validation.getErrors());
                         if (extractedParams.containsKey(LIMIT))
                            return fHousesRepository.findByParam(new BasicDBObject(params.toMap()),
                                    PageRequest.of(
                                            Integer.parseInt(extractedParams.getOrDefault("offset", 0).toString()),
                                            Integer.parseInt(extractedParams.getOrDefault("limit", fHousesRepository.findAll().size()).toString()),
                                            Sort.Direction.fromString(
                                                    extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
                        else
                            return fHousesRepository.findByParam(new BasicDBObject(params.toMap()),
                                    new Sort(Sort.Direction.fromString(
                                            extractedParams.getOrDefault(ORDER_CRITERIA, "ASC").toString()),
                                            extractedParams.getOrDefault(ORDER_BY, "id").toString()));
    }
}
