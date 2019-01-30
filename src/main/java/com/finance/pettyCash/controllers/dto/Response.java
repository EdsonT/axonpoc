package com.finance.pettyCash.controllers.dto;


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
}

