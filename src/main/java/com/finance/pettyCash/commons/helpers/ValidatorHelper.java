package com.finance.pettyCash.commons.helpers;

import org.json.JSONObject;

import java.util.List;

public class ValidatorHelper extends Throwable{
    private boolean isValid;
    private List errors;
    private JSONObject error;

    public ValidatorHelper(List errors) {
        isValid = errors.size() == 0;
        this.errors = errors;
    }

    public ValidatorHelper(JSONObject error) {
        isValid = error == null || error.toString().equals("{}");
        this.error = error;
    }

    public boolean isValid() {
        return isValid;
    }

    public List getErrors() {
        return errors;
    }

    public JSONObject getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ValidatorHelper{" +
                "isValid=" + isValid +
                ", errors=" + errors +
                '}';
    }
}