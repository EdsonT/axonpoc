package com.finance.pettyCash.commandModel.domains;

import com.finance.pettyCash.commons.enums.AccountType;

public class Balance {
    private Double realBalance;
    private Double systemBalance;
    private CurrencyModel currencyModel;
    private Double difference;
    private String financialCode;

    public void openInit(Amount amount) {
        this.currencyModel = amount.getCurrencyModel();
        this.realBalance = amount.getAmount();
        this.systemBalance = amount.getAmount();
        this.difference = this.realBalance - this.systemBalance;
    }

    public void openNormal(Amount amount, Double oldBalance) {
        this.realBalance = amount.getAmount();
        this.systemBalance = oldBalance;
        this.difference = this.realBalance - this.systemBalance;
    }

    public Double getRealBalance() {
        return realBalance;
    }

    public Double getSystemBalance() {
        return systemBalance;
    }

    public CurrencyModel getCurrencyModel() {
        return currencyModel;
    }

    public Double getDifference() {
        return difference;
    }

    public String getFinancialCode() {
        return financialCode;
    }

    public void updateBalance(Double amount, String type, Double oldBalance) {
        if (type.equalsIgnoreCase(AccountType.INCOME.toString())) {
            this.realBalance = this.realBalance + amount;
            this.systemBalance = oldBalance + amount;
        } else if (type.equalsIgnoreCase(AccountType.EXPENSE.toString())) {
            this.realBalance = this.realBalance - amount;
            this.systemBalance = oldBalance - amount;
        }
    }

    public void closeBalance(Double amount, Double oldBalance) {
        this.realBalance = amount;
        this.systemBalance = oldBalance;
        this.difference = this.realBalance - this.systemBalance;
    }

    public void redefineBalance(Double realBalance, Double systemBalance, Double difference,
                                CurrencyModel currencyModel, String financialCode) {
        this.realBalance = realBalance;
        this.systemBalance = systemBalance;
        this.difference = difference;
        this.currencyModel = currencyModel;
        this.financialCode = financialCode;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "realBalance=" + realBalance +
                ", systemBalance=" + systemBalance +
                ", difference=" + difference +
                ", financialCode=" + financialCode +
                '}';
    }
}
