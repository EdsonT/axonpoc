package com.finance.pettyCash.queryModel.finalView.accounts;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "F_accounts")
public class Account extends BaseEntity {
    private String code;
    private String name;
    private String description;
    private String accountType;
    private Boolean visible;
    private String statusCode;
    private String status;

    public Account() {
    }

    public Account(String code, String name, String description, String accountType, Boolean visible, String statusCode, String status) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.accountType = accountType;
        this.visible = visible;
        this.statusCode = statusCode;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAccountType() {
        return accountType;
    }

    public Boolean getVisible() {
        return visible;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
}
