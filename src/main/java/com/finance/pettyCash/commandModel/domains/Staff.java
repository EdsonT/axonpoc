package com.finance.pettyCash.commandModel.domains;

public class Staff {
    private String code;
    private String name;

    public Staff(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
