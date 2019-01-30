package com.finance.pettyCash.queryModel;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SearchQuery {
    @NotNull
    private String keyword;
    private List<String> fields;

    public SearchQuery(String keyword,List<String> fields){
        this.keyword=keyword;
        this.fields=fields;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "BalancePerHouseQuery{" +
                "keyword='" + keyword + '\'' +
                ", fields=" + fields +
                '}';
    }
}
