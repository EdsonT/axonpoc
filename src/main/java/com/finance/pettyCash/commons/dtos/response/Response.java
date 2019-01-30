package com.finance.pettyCash.commons.dtos.response;


import java.util.HashMap;

/**
 * @author Mirko Flores
 */
public class Response {
    private Object content;
    private Object errors;

    public Object getContent() {
        return content;
    }

    public Response setContent(Object content) {
        this.content = content;
        return this;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Response getCommandReturn(Object code) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("aggregateCode", code);
        this.content = response;
        return this;
    }
}
