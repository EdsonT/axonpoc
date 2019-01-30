package com.finance.pettyCash.services;

import com.finance.pettyCash.commons.enums.Errors;
import com.finance.pettyCash.commons.exceptions.BusinessException;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QueryValidatorService {



    public ValidatorHelper validateFindOne(String code){
      List<BusinessException> errors = new ArrayList<>();

        if(code.equals(null))
            errors.add(new BusinessException(Errors.NULL_CODE));

        return new ValidatorHelper(errors);

    }
    public ValidatorHelper paramsValidator(HashMap params, Class classProvided) {
        List<Errors> errors = new ArrayList<>();

        if(params.containsKey("limit")){
            if(params.get("limit").equals(null)||params.get("limit").toString().isEmpty())
                errors.add(Errors.NULL_LIMIT);
            else if(Integer.valueOf(params.get("limit").toString())<1)
                errors.add(Errors.INVALID_LIMIT);
        }

        if(params.containsKey("offset"))
            if(Integer.valueOf(params.get("offset").toString())<1)
                errors.add(Errors.INVALID_OFFSET);
        if(params.containsKey("orderBy"))
            try{
                Field orderBy =classProvided.getDeclaredField(params.get("orderBy").toString());

            }catch (NoSuchFieldException e) {
                    errors.add(Errors.INVALID_ORDERFIELD);
            }
        if (params.containsKey("orderCriteria"))
            if(params.get("orderCriteria").toString()!="ASC"||params.get("orderCriteria").toString()!="DESC")
                errors.add(Errors.INVALID_ORDERCRITERIA);

        return new ValidatorHelper(errors);
    }
}
