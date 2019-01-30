package com.finance.pettyCash.commons.dtos.request;

public class PettyCashRequest {
    private String formCode;
    private String aggregateCode;
    private Object content;

    public PettyCashRequest() {
    }

    public PettyCashRequest(String formCode, String aggregateCode, Object content) {
        this.formCode = formCode;
        this.aggregateCode = aggregateCode;
        this.content = content;
    }

    public String getFormCode() {
        return formCode;
    }

    public String getAggregateCode() {
        return aggregateCode;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "PettyCashRequest{" +
                "formCode='" + formCode + '\'' +
                ", aggregateCode='" + aggregateCode + '\'' +
                ", content=" + content +
                '}';
    }
}
