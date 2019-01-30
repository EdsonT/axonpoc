package com.finance.pettyCash.queryModel.operationalView.balance;

public class BalancePerHouseCmd extends BalancePerHouse {
    public BalancePerHouseCmd(){}

    public void updateBalance(Double realBalance, Double systemBalance, Double difference) {
        this.realBalance = realBalance;
        this.systemBalance = systemBalance;
        this.difference = difference;
    }

    public BalancePerHouseCmd(Double realBalance, Double systemBalance, Double difference, String houseCode,
                           String currencyCode, String currencyName, String currencySymbol) {
        super(realBalance, systemBalance, difference, houseCode, currencyCode,currencyName, currencySymbol);
    }
}
