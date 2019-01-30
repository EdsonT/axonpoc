package com.finance.pettyCash.commandModel.domains;

import com.finance.pettyCash.commons.enums.AccountType;

public class GeneralExpenseModel extends ExpenseModel {
    private String description;

    public GeneralExpenseModel(String code, String name, AccountType type, String expenseCode, String expenseName, String description) {
        super(code, name, type, expenseCode, expenseName);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
