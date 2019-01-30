package com.finance.pettyCash.commandModel.domains;

public class Person extends Entity {
    private String name;

    public Person(String code, String name) {
        super(code);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
