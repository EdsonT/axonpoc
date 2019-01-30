package com.finance.pettyCash.commons.exceptions;

import org.json.JSONObject;


public class BusinessException {

    private Enum code;
    private JSONObject error;

    public BusinessException(Enum code) {
        this.code = code;
    }

    public BusinessException(Enum code, JSONObject error) {
        this.code = code;
        this.error = error;
    }

    public Enum getCode() {
        return code;
    }

    public JSONObject getError() {
        return error;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", error=" + error +
                '}';
    }
}
