package com.finance.pettyCash.queryModel.finalView.expenses;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "F_expense")
public class Expense extends BaseEntity {

    protected String code;
    protected String name;
    protected String description;
    protected String typeCode;
    protected String typeName;
    protected String statusCode;
    protected String status;
    protected String entityAssigned;


    public Expense(){}

    public Expense(String code, String name, String description, String typeCode, String typeName, String statusCode, String status, String entityAssigned) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.statusCode = statusCode;
        this.status = status;
        this.entityAssigned = entityAssigned;
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

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getEntityAssigned() {
        return entityAssigned;
    }
}
