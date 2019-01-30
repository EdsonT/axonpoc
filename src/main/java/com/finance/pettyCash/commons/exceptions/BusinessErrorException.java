package com.finance.pettyCash.commons.exceptions;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class BusinessErrorException extends ExecutionException {
    private HashMap<String, Object> errors;

    public BusinessErrorException(HashMap<String, Object> errors) {
        super(errors.toString());
        this.errors = errors;
    }

    public HashMap<String, Object> getErrors() {
        return errors;
    }
}
