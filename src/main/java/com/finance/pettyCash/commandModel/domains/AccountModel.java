package com.finance.pettyCash.commandModel.domains;

import com.finance.pettyCash.commons.enums.AccountType;

public class AccountModel {
    private String code;
    private String name;
    private AccountType type;

    public AccountModel(String code, String name, AccountType type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }
}
