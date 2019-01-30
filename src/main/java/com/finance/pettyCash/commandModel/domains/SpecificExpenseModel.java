package com.finance.pettyCash.commandModel.domains;


import com.finance.pettyCash.commons.enums.AccountType;

public class SpecificExpenseModel extends ExpenseModel {
    private Entity assignedTo;

    public SpecificExpenseModel(String code, String name, AccountType type, String expenseCode, String expenseName, Entity assignedTo) {
        super(code, name, type, expenseCode, expenseName);
        this.assignedTo = assignedTo;
    }

    public Entity getAssignedTo() {
        return assignedTo;
    }
}
