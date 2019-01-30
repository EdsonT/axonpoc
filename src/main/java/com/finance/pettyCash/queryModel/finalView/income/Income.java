package com.finance.pettyCash.queryModel.finalView.income;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "F_income")
public class Income extends BaseEntity {

    private String code;
    private String name;
    private String statusCode;
    private String status;


    public Income(String code, String name, String statusCode,String status) {
        this.code = code;
        this.name = name;
        this.statusCode = statusCode;
        this.status = status;
    }

    public Income(){}

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
}
