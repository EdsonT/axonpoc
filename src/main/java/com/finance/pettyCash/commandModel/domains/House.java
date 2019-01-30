package com.finance.pettyCash.commandModel.domains;

public class House {
    private String code;
    private String name;

    public House(String code, String name) {
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
