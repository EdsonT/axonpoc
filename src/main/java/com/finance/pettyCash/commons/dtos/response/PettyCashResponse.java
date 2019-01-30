package com.finance.pettyCash.commons.dtos.response;

import com.finance.pettyCash.commandModel.domains.*;

import java.util.Date;

public class PettyCashResponse {
    private FinancialCode financialCode;
    private House house;
    private Amount amount;
    private Staff responsible;
    private String description;
    private Balance balance;
    private AccountModel accountModel;
    private Date requestDate;
    private Date registrationDate;

    public PettyCashResponse(FinancialCode financialCode, House house, Amount amount, Staff responsible,
                             String description, Balance balance, AccountModel accountModel, Date requestDate, Date registrationDate) {
        this.financialCode = financialCode;
        this.house = house;
        this.amount = amount;
        this.responsible = responsible;
        this.description = description;
        this.balance = balance;
        this.accountModel = accountModel;
        this.requestDate = requestDate;
        this.registrationDate = registrationDate;
    }

    public FinancialCode getFinancialCode() {
        return financialCode;
    }

    public House getHouse() {
        return house;
    }

    public Amount getAmount() {
        return amount;
    }

    public Staff getResponsible() {
        return responsible;
    }

    public String getDescription() {
        return description;
    }

    public Balance getBalance() {
        return balance;
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
