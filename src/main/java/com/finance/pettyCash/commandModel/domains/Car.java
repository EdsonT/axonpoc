package com.finance.pettyCash.commandModel.domains;

public class Car extends Entity {
    private String brand;

    public Car(String code, String brand) {
        super(code);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
