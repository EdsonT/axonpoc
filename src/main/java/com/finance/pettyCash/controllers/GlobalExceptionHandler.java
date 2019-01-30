package com.finance.pettyCash.controllers;

import com.finance.pettyCash.commons.dtos.response.Response;
import com.finance.pettyCash.commons.enums.Errors;
import com.finance.pettyCash.commons.exceptions.BaseException;
import com.finance.pettyCash.commons.exceptions.BusinessErrorException;
import com.finance.pettyCash.commons.exceptions.BusinessException;
import com.finance.pettyCash.services.BusinessErrorService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.concurrent.ExecutionException;

//@SuppressWarnings(value = "unchecked")
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    BusinessErrorService businessErrorService;

    private ResponseEntity getErrors(List<BusinessException> errorServerList) {
        Map errorInformation = businessErrorService.getResponseErrors(errorServerList);
        Response response = new Response();
        response.setErrors(errorInformation);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(JSONException.class)
    private ResponseEntity getJsonCreation(Throwable throwable) {
        throwable.printStackTrace();
        return getErrors(Arrays.asList(new BusinessException(Errors.JSON_FORMAT_NOT_VALID)));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleError(BaseException throwable) {
        List<BusinessException> errors = new ArrayList<>();
        for (Errors error : throwable.getErrorServer()) {
            errors.add(new BusinessException(error));
        }
        return getErrors(errors);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity interruptedException(InterruptedException ve) {
        Response response = new Response();
        HashMap<String, Object> generic = new HashMap<>();
        HashMap<String, Object> error = new HashMap<>();
        generic.put("message", "unexpected error");
        generic.put("information", businessErrorService.getMessageError(ve.getCause()));
        error.put("generic", generic);
        response.setErrors(error);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity executionException(ExecutionException e) {
        Response response = new Response();
        if (e.getCause() instanceof BaseException)
            return handleError((BaseException)e.getCause());
        else if (e.getCause() instanceof BusinessErrorException) {
                response.setErrors(((BusinessErrorException) e.getCause()).getErrors());
            } else {
                HashMap<String, Object> generic = new HashMap<>();
                HashMap<String, Object> error = new HashMap<>();
                generic.put("message", "unexpected error");
                generic.put("information", businessErrorService.getMessageError(e.getCause()));
                error.put("generic", generic);
                response.setErrors(error);
            }
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity RestClientException(RestClientException e) {
        Response response = new Response();
        if (e.getCause() instanceof BaseException)
            return handleError((BaseException)e.getCause());
        else if (e.getCause() instanceof BusinessErrorException) {
            response.setErrors(((BusinessErrorException) e.getCause()).getErrors());
        } else {
            HashMap<String, Object> generic = new HashMap<>();
            HashMap<String, Object> error = new HashMap<>();
            generic.put("message", "Rest Client error");
            generic.put("information", businessErrorService.getMessageError(e.getCause()));
            error.put("generic", generic);
            response.setErrors(error);
        }
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity HttpClientException(HttpServerErrorException e) {
        Response response = new Response();
        if (e.getCause() instanceof BaseException)
            return handleError((BaseException)e.getCause());
        else if (e.getCause() instanceof BusinessErrorException) {
            response.setErrors(((BusinessErrorException) e.getCause()).getErrors());
        } else {
            HashMap<String, Object> generic = new HashMap<>();
            HashMap<String, Object> error = new HashMap<>();
            generic.put("message", "Rest Client error");
            generic.put("information", businessErrorService.getMessageError(e.getCause()));
            error.put("generic", generic);
            response.setErrors(error);
        }
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(BusinessErrorException.class)
    public ResponseEntity businessException(BusinessErrorException e) {
        Response response = new Response();
        response.setErrors(((BusinessErrorException) e.getCause()).getErrors());
        return ResponseEntity.ok(response);
    }
}
