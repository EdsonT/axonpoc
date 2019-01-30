package com.finance.pettyCash.commandModel.domains;

import com.finance.pettyCash.commons.enums.AccountType;

public class IncomeModel extends AccountModel {
    private String sourceMoney;
    private String sourceCode;

    public IncomeModel(String code, String name, AccountType type, String sourceMoney, String sourceCode) {
        super(code, name, type);
        this.sourceMoney = sourceMoney;
        this.sourceCode = sourceCode;
    }

    public String getSourceMoney() {
        return sourceMoney;
    }

    public String getSourceCode() {
        return sourceCode;
    }
}
