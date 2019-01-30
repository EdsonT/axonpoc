package com.finance.pettyCash.services;


import com.finance.pettyCash.commons.enums.Errors;
import com.finance.pettyCash.commons.exceptions.BusinessException;
import com.finance.pettyCash.commons.exceptions.ErrorInformation;
import com.finance.pettyCash.queryModel.finalView.errors.ErrorRepository;
import com.finance.pettyCash.queryModel.finalView.errors.ErrorView;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class BusinessErrorService {
    @Autowired
    ErrorRepository errorRepository;

    public ErrorInformation getErrorByAlias(String alias) {
        ErrorView error = errorRepository.findByAlias(alias);
        if (error != null)
            return new ErrorInformation(error.getAlias(), error.getField(), error.getMessage());
        return null;
    }

    public HashMap<String, Object> getResponseErrors(List<BusinessException> errors) {
        HashMap<String, Object> errorResponse = new HashMap();
        for (BusinessException error : errors) {
            if (error.getCode() == Errors.VALIDATION_JSON_SCHEMA) {
                errorResponse = (new Gson()).fromJson(error.getError().toString(), HashMap.class);
            } else {
                ErrorInformation errorInformation = getErrorByAlias(error.getCode().toString());
                if (errorInformation == null) {
                    errorInformation = new ErrorInformation();
                    errorInformation.defaultError(error.getCode(), "not found");
                }
                if (errorResponse.isEmpty() || !errorResponse.containsKey(errorInformation.getField())) {
                    errorResponse.put(errorInformation.getField(), errorInformation);
                } else if (errorResponse.containsKey(errorInformation.getField())) {
                    ErrorInformation errorAnt = (ErrorInformation) errorResponse.get(errorInformation.getField());
                    errorAnt.updateMessage(errorInformation.getMessage());
                    errorResponse.replace(errorInformation.getField(), errorAnt);
                }
            }
        }
        return errorResponse;
    }

    public HashMap<String, Object> getResponseErrors(JSONObject error) {
        List<BusinessException> errors = Arrays.asList(new BusinessException(Errors.VALIDATION_JSON_SCHEMA, error));
        return getResponseErrors(errors);
    }

    public List<String> getMessageError(Throwable cause) {
        List<String> errors = new ArrayList<>();
        errors.add("Exception class: " + cause.getClass().getSimpleName());
        errors.add("Localized message: " + cause.getLocalizedMessage());
        for (StackTraceElement s : cause.getStackTrace()) {
            errors.add("File: " + s.getFileName() + " Line: " + s.getLineNumber() + " Class: " + s.getClassName() + " Method: " +
                    s.getMethodName());
        }
        return errors;
    }
}