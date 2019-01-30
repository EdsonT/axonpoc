package com.finance.pettyCash.queryModel.finalView.expenses;

public class ExpenseCmd extends Expense {

    public ExpenseCmd(){ super();}

    public ExpenseCmd(String code, String name, String description, String typeCode, String typeName, String statusCode, String status, String entityAssigned) {
        super(code, name, description, typeCode, typeName, statusCode, status, entityAssigned);
    }
    public ExpenseCmd(String code, String name, String description, String typeCode, String typeName, String entityAssigned) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.entityAssigned = entityAssigned;
    }
}
