package com.finance.pettyCash.commons.dtos.data;

import java.util.Date;

public class EntryData {
    private String houseCode;
    private String responsibleCode;
    private String currencyCode;
    private Double amount;
    private String description;
    private String accountCode;
    private String movementCode;
    private String entityCode; //Expense Assigned to (entityCode)
    private Date requestDate; // Correct Entry

    public EntryData(String houseCode, String responsibleCode, String currencyCode, Double amount,
                     String description, String accountCode, String movementCode) {
        this.houseCode = houseCode;
        this.responsibleCode = responsibleCode;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.description = description;
        this.accountCode = accountCode;
        this.movementCode = movementCode;
    }

    public EntryData(String houseCode, String responsibleCode, String currencyCode, Double amount,
                     String description, String accountCode, String movementCode, String entityCode) {
        new EntryData(houseCode, responsibleCode, currencyCode, amount, description, accountCode, movementCode);
        this.entityCode = entityCode;
    }

    public EntryData(String houseCode, String responsibleCode, String currencyCode, Double amount,
                     String description, String accountCode, String movementCode, Date requestDate) {
        new EntryData(houseCode, responsibleCode, currencyCode, amount, description, accountCode, movementCode);
        this.requestDate = requestDate;
    }

    public EntryData(String houseCode, String responsibleCode, String currencyCode, Double amount,
                     String description, String accountCode, String movementCode, String entityCode, Date requestDate) {
        new EntryData(houseCode, responsibleCode, currencyCode, amount, description, accountCode, movementCode, entityCode);
        this.requestDate = requestDate;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getResponsibleCode() {
        return responsibleCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public String getMovementCode() {
        return movementCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public Date getRequestDate() {
        return requestDate;
    }
}
