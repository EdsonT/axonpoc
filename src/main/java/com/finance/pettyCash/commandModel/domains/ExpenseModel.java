package com.finance.pettyCash.commandModel.domains;

import com.finance.pettyCash.commons.enums.AccountType;

public class ExpenseModel extends AccountModel {
    private String expenseCode;
    private String expenseName;

    public ExpenseModel(String code, String name, AccountType type, String expenseCode, String expenseName) {
        super(code, name, type);
        this.expenseCode = expenseCode;
        this.expenseName = expenseName;
    }

    public String getExpenseCode() {
        return expenseCode;
    }

    public String getExpenseName() {
        return expenseName;
    }
}
