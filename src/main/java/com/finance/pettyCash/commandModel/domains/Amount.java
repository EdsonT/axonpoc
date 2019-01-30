package com.finance.pettyCash.commandModel.domains;

public class Amount {
    private CurrencyModel currencyModel;
    private Double amount;

    public Amount(CurrencyModel currencyModel, Double amount) {
        this.currencyModel = currencyModel;
        this.amount = amount;
    }

    public CurrencyModel getCurrencyModel() {
        return currencyModel;
    }

    public Double getAmount() {
        return amount;
    }
}
