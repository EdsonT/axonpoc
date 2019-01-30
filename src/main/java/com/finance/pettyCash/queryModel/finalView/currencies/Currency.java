package com.finance.pettyCash.queryModel.finalView.currencies;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "F_currencies")
public class Currency extends BaseEntity {
    private String code;
    private String name;
    private String symbol;
    private Boolean baseCurrency;
    private String statusCode;
    private String status;


    public Currency(){}

    public Currency(String code, String name, String symbol, Boolean baseCurrency, String statusCode, String status) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.baseCurrency = baseCurrency;
        this.statusCode = statusCode;
        this.status = status;
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Boolean getBaseCurrency() {
        return baseCurrency;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
}
