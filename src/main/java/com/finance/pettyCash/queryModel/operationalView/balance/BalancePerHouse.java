package com.finance.pettyCash.queryModel.operationalView.balance;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "O_balancePerHouse")
public class BalancePerHouse extends BaseEntity {
    @Field(value = "balance")
    protected Double realBalance;
    protected Double systemBalance;
    protected Double difference;
    protected String houseCode;
    protected String currencyCode;
    protected String currencyName;
    protected String currencySymbol;

    public BalancePerHouse(Double realBalance, Double systemBalance, Double difference, String houseCode,
                           String currencyCode, String currencyName, String currencySymbol) {
        this.realBalance = realBalance;
        this.systemBalance = systemBalance;
        this.difference = difference;
        this.houseCode = houseCode;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
    }

    public BalancePerHouse(){}


    public Double getRealBalance() {
        return realBalance;
    }

    public Double getSystemBalance() {
        return systemBalance;
    }

    public Double getDifference() {
        return difference;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

}
