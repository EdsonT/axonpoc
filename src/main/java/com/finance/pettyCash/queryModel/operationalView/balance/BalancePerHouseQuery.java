package com.finance.pettyCash.queryModel.operationalView.balance;


public class BalancePerHouseQuery extends BalancePerHouse {

    public BalancePerHouseQuery(Double realBalance, Double systemBalance, Double difference, String houseCode,
                           String currencyCode, String currencyName, String currencySymbol) {
        super(realBalance, systemBalance, difference, houseCode, currencyCode, currencyName, currencySymbol);
    }

}
